#Promotions app for Abercrombie & Fitch
Project for A&F coding test, 
It uses SQLite for data persistence of the feed info, SharedPreferences for cachedata of the user name and uses Intent extras for comunication between Activities. 

*For parsing the JSon from the servet I try to do it by a simple library called GSon but the source is not properly structured, then a specific parser was implemented to handle the errors in the source. 

# Start Screen
Ask for the name of the user if it was already set this screen only shows a loader for 3 seconds and then automatically shows the main screen.

![img001](https://cloud.githubusercontent.com/assets/7500868/10745020/d077ce82-7c03-11e5-9dd9-cde9905dbc27.png)

# Main Screen
If the user has already set its name, the name is concatenated to the welcome string.

The data fetched from the service is displayed in a grid, being each item a button.

If the app has no info saved and it cannot reach the server a slideshow with products is shown until the device reaches the server. After the image is aquiered it is saved in the SQLite DB.

If the app isn't close or it has already fetched, the promotions are loaded from the local saved data.

![img002](https://cloud.githubusercontent.com/assets/7500868/10745022/d07882fa-7c03-11e5-8e7a-5fb01ba75685.png)

The app runs separated threads to fetch the images of each promotion

![img003](https://cloud.githubusercontent.com/assets/7500868/10745021/d077ee76-7c03-11e5-9bdc-ed0fac422c0e.png)

# Detail Promo screen
The complete information of the promotion is displayed in this screen.

* The feed has html code on it that is displayed in the screenshot, it can be suppressed but a more detail to handle the issue is needed

![img004](https://cloud.githubusercontent.com/assets/7500868/10745023/d07b05ca-7c03-11e5-8ee5-3cf95c22ceef.png)

# Target 
The target of the promotion is open as a popup in the detail screen.

![img005](https://cloud.githubusercontent.com/assets/7500868/10745024/d07dc710-7c03-11e5-861e-523812f67457.png)
