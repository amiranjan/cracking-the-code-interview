# https://api.codesignalcontent.com/stockPrices/api/getPrices?date=20220103
# The date for which the currency stock quoted are being requested in YYYYMMDD format

# Assuming that the current date is 31012022, extract the data from 01012022 to 31012022 using the APIs provided.

# The data quality issues generally revolve around missing quotes for certain days. For dates where #the stock price is missing, use the most recent valid price at that given point in time.


from datetime import date, timedelta
import urllib.request
import json

start_date = date(2022, 1, 1)
end_date = date(2022, 1, 31)

def daterange(start_date, end_date):
    for n in range(int((end_date - start_date).days) + 1):
        yield start_date + timedelta(n)

stock_prices = {}
for single_date in daterange(start_date, end_date):
    date = int(single_date.strftime("%Y%m%d"))
    url = "https://api.codesignalcontent.com/stockPrices/api/getPrices?date={}".format(date)
    try:
        response = urllib.request.urlopen(url)
        stock_price = json.loads(response.read().decode('utf-8'))
        prices = stock_price['prices']
        for key in prices:
            if prices[key] == '':
                print("Data Quality Issue for {} on {}. Updating it with the price on {}".format(key, date, date - 1))
                if date-1 in stock_prices:
                    prices[key] = stock_prices[date-1][key]
        stock_prices[date] = prices
    except urllib.error.HTTPError as e:
        if e.code == 400 and date-1 in stock_prices:
            stock_prices[date] = stock_prices[date-1]
print(stock_prices)