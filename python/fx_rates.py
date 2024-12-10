# import requests
# import mysql.connector
# import pandas as pd

"""
Operating at the scale of Capital One often includes dealing with foreign currencies and businesses. Getting the appropriate foreign currency exchange rate is crucial for any international business processes. One of the business units has requested your help to set up a microservice that pulls the latest foreign exchange rates from an API and stores them into a database for analysis.

The API delivers various exchange rates for the core currencies that Capital One operates with - USD, GBP, EUR, JPY and CNY. The base URL is: https://api.codesignalcontent.com/forex/api/rates/getRates?date=2022-01-01

Assuming that the current date is 2022-02-01, extract the data from 2022-01-01 to 2022-01-31 (inclusive of both start and end dates) using the APIs provided. Store the raw data in a raw_exchange_rate_database folder as a single csv file.

"""

from datetime import date, timedelta, datetime
import urllib.request
import json

def date_range(start_date: date, end_date: date):
    for n in range(int((end_date - start_date).days) + 1):
        yield start_date + timedelta(n)

def getRates(start_date, end_date):
    currency_dict = {}
    for date in date_range(start_date, end_date):
        url = "https://api.codesignalcontent.com/forex/api/rates/getRates?date={}".format(date)
        response = urllib.request.urlopen(url)
        data = json.loads(response.read().decode('utf-8'))
        currency_dict[data['date']] = data['rate']
    return currency_dict

def get_from_previous_date(currencies, date, currency):
    previous_date = datetime.strftime(datetime.strptime(date, '%Y-%m-%d') - timedelta(1), '%Y-%m-%d')
    while previous_date not in currencies.keys() or currency not in currencies[previous_date].keys() or str(currencies[previous_date][currency]) == '':
        previous_date = datetime.strftime(datetime.strptime(previous_date, '%Y-%m-%d') - timedelta(1), '%Y-%m-%d')
    value = currencies[previous_date][currency]
    print("Previous Date: {}, {}={}".format(previous_date, currency, value))
    return value

def dq_check(currencies):
    for date in currencies:
        for currency in currencies[date]:
            value = str(currencies[date][currency])
            value = value.replace("$", "")
            if value == 'n/a' or value == '':
                value = get_from_previous_date(currencies, date, currency)
            currencies[date][currency] = value
    return currencies

def flatten_currencies(currencies):
    line = 'date, currency, rate\n'
    currency_data = [line]
    for date in currencies.keys():
        currency_map = currencies[date]
        for key in currency_map:
            line = date + ', ' + key + "," + str(currency_map[key]) + '\n'
            currency_data.append(line)
    return currency_data

def writeIntoCSV(currencies):
    with open('fx_rates.csv', mode = "w") as file:
        file.writelines(currencies)


if __name__ == "__main__":
    start_date = date(2022,1,1)
    end_date = date(2022,1,31)
    currencies_rates = getRates(start_date, end_date)
    #print(currencies_rates)
    distinct_currencies = set()
    for date, currencies in currencies_rates.items():
        for currency, rate in currencies.items():
            distinct_currencies.add(currency)
    print(distinct_currencies)
    currencies = dq_check(currencies_rates)
    print("*** After DQ Check *****")
    print(currencies)
    currency_data = flatten_currencies(currencies)
    writeIntoCSV(currency_data)
