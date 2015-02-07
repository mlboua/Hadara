# -*- coding: utf-8 -*-
import re
import sys

def findSetsDecl(text):
    ''' Retrouve toutes les déclarations de SET dans le fichier et les ramplace'''

    setRegexp = '[A-Za-z_0-9]+\s*=\s*\{\s*[A-Za-z_0-9]+\s*\n*[,\n*\s*[A-Za-z_0-9]+\s*]*\}'
    i = 0
    setContentArray = dict()
    setToPut = dict()
    foundPatterns = re.findall(setRegexp, txt)

    for p in foundPatterns:
        setElt = p.split('=')
        setName = setElt[0]
        setCont = re.sub('{', '', setElt[1])
        setCont = re.sub('}', '', setCont).split(',')
        for c in setCont:
            i = i+1
            setContentArray[c] = str(i)
        setToPut[setName] = setContentArray
    return setContentArray



#-----------------------------------------------------------------------

if len(sys.argv) != 3 :
    raise Exception("Vous devez indiquer les noms des fichiers (source et resultat) comme argument de la commande")
inputFile = sys.argv[1]
outputFile = sys.argv[2]
txt = ''
with open(inputFile,'r') as myFile:
    txt = myFile.read()
 
ptxt = findSetsDecl(txt)
for key,val in ptxt.items():
    txt = re.sub(r'\b'+key.strip(), val, txt)

with open(outputFile,'w') as outFile:
    outFile.write(txt)
