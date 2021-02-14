
### Kill Server
kill -9 `sudo lsof -t -i:8080`
kill -9 `sudo lsof -t -i:8081`


RUN backend using

mvn spring-boot:run


Access Frontend by opening live server on vscode and goto index.html

public api
front end - https://xmeme-fe.netlify.app/meme.html
backend - https://infinite-beach-28510.herokuapp.com/memes