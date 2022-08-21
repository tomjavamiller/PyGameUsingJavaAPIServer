import requests

url = 'http://localhost:8080/mastermind/genboard?numpgs=5&numcolors=8'

r = requests.get(url)
if not r.ok:
  print("Didn't work: {} Text: {}".format(url,r.text))
  exit()
 
print("the json was: {}".format(r.json()))
board = r.json()["board"]
print("board = {}".format(board))

url = 'http://localhost:8080/mastermind/guess'
aGuess = [1,1,2,2,2]
r = requests.post(url,json={"guess":aGuess,"board":board})
if not r.ok:
  print("didn't work: "+ url)
  print(r.text)
  exit()
 
print("your guess results:" + str(r.json()))

# {numCorrect: 0, numColors: 3}
numCorrect = r.json()["numCorrect"]
numColors = r.json()["numColors"]
#print("numCorrect: " +str(numCorrect))
#print("numColors: "+str(numColors))

# brute force solution
# start with all -1
aGuess = [-1,-1,-1,-1,-1]
for inx in range(5):
   for iny in range(8):
     aGuess[inx] = iny
     r = requests.post(url,json={"guess":aGuess,"board":board})
     numCorrect = r.json()["numCorrect"]
     print("next guess: {} grade: > {} ".format(aGuess,numCorrect,inx))
     if numCorrect > inx:
        break
print("this is it:" + str(aGuess))
