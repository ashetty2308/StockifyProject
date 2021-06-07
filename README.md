# Stockify 

Stockify is an android application that allows those who are new to the stock world to dip their feet into the trading & investment world.

Built with: Alpha Vantage, Financial Modeling Prep, Newsdata.io, and Firebase.

Features:

- The latest stock market headlines and stories, so you can keep up with all of the buzzing news on Wall Street.
- Ticker Symbol Search Feature: users can search by company names (i.e.; Starbucks, Microsoft, Target, Apple, etc.) to get a list of possible ticker symbols (SBUX, MSFT, TGT, AAPL, etc.) 
- Users can also search for any stock they want by the ticker symbol. After doing this, the user will have a generated graph, which depicts a 2 day trend of the stock they are interested in (compares yesterday's high to today's current price of the stock). 
  - It's color coded, so the user can see if it is a positive trend (green), or negative trend (red)
- Users can save any news headlines that they want.
- These articles are connected to firebase, and the saved headlines are displayed in the "Saved" section of the app, where a list of all the headines saved will appear.

GraphView: https://github.com/jjoe64/GraphView

***************

Splash Screen: 

<img src = "https://user-images.githubusercontent.com/77937577/120901280-db39c880-c607-11eb-9a58-304972145432.png" width = "250" height = "450">

***************

Home Screen (Headlines): 

<img src = "https://user-images.githubusercontent.com/77937577/120901286-e260d680-c607-11eb-81c7-a2932621d7be.png" width = "250" height = "450">

***************

News Activity Example: 

<img src = "https://user-images.githubusercontent.com/77937577/120901308-fb698780-c607-11eb-9135-db570676d9a8.png" width = "250" height = "450">

**Note that the image loaded at the top is either a generic wall street image, or the actual image used by the news article. However, sometimes the Newsdata.io API doesn't provide the image URL, so I can't load anything with Picasso. In that case, I load a generic wall street image.**


***************

Ticker Search:

<img src = "https://user-images.githubusercontent.com/77937577/120901316-0c19fd80-c608-11eb-90b9-39909c663725.png" width = "250" height = "450">

***************

Stock Data Search: 

<img src = "https://user-images.githubusercontent.com/77937577/120901324-1805bf80-c608-11eb-8672-a03b72a9f987.png" width = "250" height = "450">

<img src = "https://user-images.githubusercontent.com/77937577/121035109-8fa82b80-c77b-11eb-9f84-9074530c15fd.png" width = "250" height = "450">

<img src = "https://user-images.githubusercontent.com/77937577/120901330-20f69100-c608-11eb-9730-dd7c464c7f41.png" width = "250" height = "450">
 
 (This was an older screenshot, so the bottom navigation bar looks a little bit different)                                     

***************

Firebase: 

<img src = "https://user-images.githubusercontent.com/77937577/120901378-703cc180-c608-11eb-999a-daec895f8eb3.PNG" width = "1500" height = "450">

<img src = "https://user-images.githubusercontent.com/77937577/120901379-729f1b80-c608-11eb-9d86-ec9d991d2983.png" width = "250" height = "450">
