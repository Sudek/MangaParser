# MangaParser
This is the server side android application MangaTracker. MangaParser parse all titels via custom proxy.
At present we have one POST method and support only one source (mangafox.com). 

Request address is "http://localhost:4567/odmin/fetch/source". Successful request return "ok".

Example POST request:

"http://localhost:4567/odmin/fetch/mangafox?ip=177.102.239.243&port=8080&startTitle=0&endTitle=50&source=mangafox".

Where is: 

1)mangafox - source address (mangafox.com/manga)

2)177.102.239.243 - proxy ip address

3)8080 - proxy port

4)0 - first title in total list of titles

5)50 - last title in total list of titles

10.09.2016 Total titles - 17392.
