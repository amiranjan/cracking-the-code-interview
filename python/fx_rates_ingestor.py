"""
Operating at the scale of Capital One often includes dealing with foreign currencies and businesses. Getting the appropriate foreign currency exchange rate is crucial for any international business processes. One of the business units has requested your help to set up a microservice that pulls the latest foreign exchange rates from an API and stores them into a database for analysis.

The API delivers various exchange rates for the core currencies that Capital One operates with - USD, GBP, EUR, JPY and CNY. The base URL is: https://api.codesignalcontent.com/forex/api/rates/getRates?date=2022-01-01

Assuming that the current date is 2022-02-01, extract the data from 2022-01-01 to 2022-01-31 (inclusive of both start and end dates) using the APIs provided. Store the raw data in a raw_exchange_rate_database folder as a single csv file.

"""
import datetime
from datetime import date, timedelta
import urllib.request
import json


def date_range(start_date: date, end_date: date):
    for n in range(int((end_date - start_date).days)):
        yield start_date + timedelta(n)

def getRate(rate_date: date) -> dict[str, str]:
    url = "https://api.codesignalcontent.com/forex/api/rates/getRates?date={}".format(rate_date)
    response = urllib.request.urlopen(url)
    return json.loads(response.read().decode("utf-8"))['rate']

def fetch_rate_from_previous_date(rate_date: date, currency: str, current_rate: str, fx_rates: dict[str, dict[str, str]]) -> str:
    previous_date = rate_date - timedelta(1)
    previous_date_str = str(previous_date)
    while previous_date_str != '2021-12-31' and currency not in fx_rates[previous_date_str].keys():
        previous_date = previous_date - timedelta(1)
        previous_date_str = str(previous_date)
        print("Rates not available for {}={} on {} hence looking up on {}".format(currency, current_rate, str(rate_date), previous_date_str))
    if previous_date_str != '2021-12-31':
        current_rate = fx_rates[previous_date_str][currency]
    return current_rate

def remove_invalid_currencies(currency_wise_rates: dict[str, str], rate_date: date, currencies_to_remove: list[str]):
    for currency in currencies_to_remove:
        print("Removing currency '{}' on {}".format(currency, rate_date))
        currency_wise_rates.pop(currency)

def fix_rates(currency_wise_rates: dict[str, str], rate_date: date, fx_rates: dict[str, dict[str, str]]):
    invalid_currencies = []
    for currency, rate in currency_wise_rates.items():
        trimmed_currency = currency.strip()
        if trimmed_currency != currency:
            invalid_currencies.append(currency)
        rate = str(rate).replace("$", "")
        if rate in ['n/a', '']:
            rate = fetch_rate_from_previous_date(rate_date, trimmed_currency, rate, fx_rates)

        currency_wise_rates[trimmed_currency] = rate

    remove_invalid_currencies(currency_wise_rates, rate_date, invalid_currencies)



if __name__ == "__main__":
    start_date, end_date = datetime.date(2022, 1, 1), datetime.date(2022, 1, 31)
    fx_rates = {}
    with open("fx_rates.csv", "w") as csv:
        csv.write('date, currency, rate\n')
        for date in date_range(start_date, end_date):
            currency_wise_rates = getRate(date)
            fix_rates(currency_wise_rates, date, fx_rates)
            fx_rates[str(date)] = currency_wise_rates
            for currency, rate in currency_wise_rates.items():
                line_items = [str(date), ', ', currency, ', ', rate, '\n']
                line = ''.join(line_items)
                csv.write(line)
    print(fx_rates)