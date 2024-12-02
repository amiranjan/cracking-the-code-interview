# https://api.codesignalcontent.com/stockPrices/api/getPrices?date=20220103
# The date for which the currency stock quoted are being requested in YYYYMMDD format

# Assuming that the current date is 31012022, extract the data from 01012022 to 31012022 using the APIs provided.

# The data quality issues generally revolve around missing quotes for certain days. For dates where #the stock price is missing, use the most recent valid price at that given point in time.


from datetime import date, timedelta
import urllib.request
import json

class StockPrice:
    def daterange(self, start_date, end_date):
        for n in range(int((end_date - start_date).days) + 1):
            yield start_date + timedelta(n)

    def getStockPrices(self, start_date, end_date):
        stock_prices = {}
        for date in self.daterange(start_date, end_date):
            date = int(date.strftime("%Y%m%d"))
            url = "https://api.codesignalcontent.com/stockPrices/api/getPrices?date={}".format(date)
            try:
                response = urllib.request.urlopen(url)
                stock_price = json.loads(response.read().decode('utf-8'))
                stock_prices[stock_price['date']] = stock_price['prices']
            except urllib.error.HTTPError as e:
                if e.code == 400 and date-1 in stock_prices:
                    stock_prices[date] = stock_prices[date-1]
        return stock_prices

    def data_quality_check_and_fix(self, stock_prices):
        for date in stock_prices.keys():
            for key, value in stock_prices[date].items():
                if value == '':
                    previous_date = str(int(date) - 1)
                    if previous_date in stock_prices:
                        print("Data Quality Issue for {} on {}. Updating it with the price on {}".format(key, date, previous_date))
                        stock_prices[date][key] = stock_prices[previous_date][key]
        return stock_prices

if __name__ == "__main__":
    start_date = date(2022, 1, 1)
    end_date = date(2022, 1, 31)
    stockPrice = StockPrice()
    stock_prices = stockPrice.getStockPrices(start_date, end_date)
    print(stock_prices)
    fixed_stock_prices = stockPrice.data_quality_check_and_fix(stock_prices)
    print(fixed_stock_prices)
