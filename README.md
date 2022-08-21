# PyGameUsingJavaAPIServer
WIP: Python Game graphic MasterMind game to call Java SpringBoot API to generate the game board and the do the grading.

Just wanting to start learning micro-services and create ~RESTful Java with SpringBoot.

# Two URL's gen and guess
* localhost:8080/mastermind/genboard
```mermaid
graph LR
A[PyGame<br/>MasterMind] -- Request board board --> B{Java<br/>API Server}
B -- Json New Board --> A
```
* localhost:8080/mastermind/guess with json
```mermaid
graph LR
A[PyGame MasterMind] -- Json guess code with board number --> B{Java}
B -- Json grade numCorrect, numColors --> A
```

# UML Sequence Diagram

```mermaid
sequenceDiagram
Python->> Java: get:getnboard(numpgs,numcolors)
Java-->>Python: json {"board":123456}
Note right of Python: Now Python repeats gussing until numCorrect is 5.
Python->> Java: guess {"guess":[1,2,3,4,5],"board":123456}
Java-->>Python: json {"numCorrect":1,"numColors":2}
Note right of Python: *<br/>*<br/>*
Python->> Java: guess {"guess":[2,2,4,5,1],"board":123456}
Java-->>Python: json {"numCorrect":5,"numColors":0}
```


