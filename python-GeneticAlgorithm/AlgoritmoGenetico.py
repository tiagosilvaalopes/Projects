import AutomatoCelular as AC
import random
from random import randint
import operator
import numpy
from collections import Counter

def initialize_pool(k, ls, nt):
    T = []
    temp = []
    for i in range(nt):
        temp = AC.create_lattice(ls, k)
        """ while temp in T:
            temp = AC.create_lattice(ls, k) """
        T.append(temp)

    return T

def initialize_population(r, k, np):
    P = []

    neighborhoodSize = 2*r + 1
    numberOfPossibleRules = k**k**neighborhoodSize

    for i in range(np):
        rule = AC.generate_rule(randint(0, numberOfPossibleRules), r, k)
        if rule not in P:
            P.append(rule)

    P = list(map(int, P))
    return P

def score_synchronization(matrix_espaco_tempo):
    countOnes = matrix_espaco_tempo[-4].count(1)
    countZeros = len(matrix_espaco_tempo[-4]) - countOnes

    if countOnes > countZeros:
        C = countOnes + matrix_espaco_tempo[-3].count(
            0) + matrix_espaco_tempo[-2].count(1) + matrix_espaco_tempo[-1].count(0)
    else:
        C = countZeros + matrix_espaco_tempo[-3].count(
            1) + matrix_espaco_tempo[-2].count(0) + matrix_espaco_tempo[-1].count(1)

    return float(C/(4*len(matrix_espaco_tempo[-1])))

def fitness_synchronization(phi, T, r, k):
    totalScores = 0

    for lattice in T:
        matriz = AC.run_ca(lattice, phi, r, k)
        totalScores = totalScores + score_synchronization(matriz)

    return totalScores/len(T)

def crossover(parent1, parent2, points):
    parent1 = (str)(parent1)
    parent2 = (str)(parent2)

    firstPartOfParent1 = parent1[:len(parent1) - points]
    secondPartOfParent1 = parent1[len(parent1) - points:]

    firstPartOfParent2 = parent2[:len(parent2) - points]
    secondPartOfParent2 = parent2[len(parent2) - points:]

    offSpring1 = (int)(firstPartOfParent1 + secondPartOfParent2)
    offSpring2 = (int)(firstPartOfParent2 + secondPartOfParent1)

    return offSpring1, offSpring2

def mutate(phi, m): #NAO FUNCIONA
    stringPhi = str(phi)
    listaPhi = list(stringPhi)
    flag = False
    for i in range(len(listaPhi)):
        if random.uniform(0, 1) < m:
            flag = True
            if listaPhi[i] == 0:
                listaPhi[i] = 1
            else:
                listaPhi[i] = 0

    novaStringPhi = ""

    for char in listaPhi:
        novaStringPhi = novaStringPhi + char
    
    phi = int(novaStringPhi)
    print("Mutation = ", flag)
    return phi

def run_generation(P, T, e, r, k, points, m):
    offSpring = []
    elite = []
    ruleScoreDictionary = {}
    offSpringLength = len(P) - e

    print("Creating Elite...")
    for i in range(len(P)):
        ruleScoreDictionary[P[i]] = fitness_synchronization(P[i], T, r, k)
        print("Rule: ", P[i], " has got a score of ",
              ruleScoreDictionary[P[i]])

    counter = Counter(ruleScoreDictionary)
    highest = counter.most_common(e)

    for rule in highest:
        elite.append(rule[0])  # to append only the key
    print("Elite complete!")

    # fazer crossover
    print("Making crossovers...")
    crossovers = 0
    while len(offSpring) < offSpringLength:
        parent1 = randint(0, e-1)
        parent2 = randint(0, e-1)
        while parent1 == parent2:
            parent2 = randint(0, e-1)
        result = crossover(elite[parent1], elite[parent2], points)

        offSpring.append(result[0])
        offSpring.append(result[1])

        crossovers = crossovers + 2

    # fazer mutation - NAO ESTA A FUNCIONAR
    """ print("Creating mutations...")
    for i in range(offSpringLength):
        offSpring.append(mutate(P[i+e], m)) """

    print("Next generation ready!!")
    return elite + offSpring  # new generation

def run_GA(r, k, e, np, nt, points, tm, m, termination):
    P = initialize_population(r, k, np)
   
    for y in range(tm):
        print('Generation ', y+1)
        T = initialize_pool(k, ls, nt)
        rules = run_generation(P, T, e, r, k, points, m)
        P.clear()
        P = rules
   
    print(rules)

def listToInt(lista):
    novaString = ""

    for char in lista:
        novaString = novaString + char
    
    num = int(novaString)
    return num
    
# testes
r = 1
k = 2
ls = 10
np = 20
nt = 10
e = 5
points = 1
m = 0.025
termination = "whatever"

P = initialize_population(r, k, np)
T = initialize_pool(k, ls, 50)

# print(fitness_synchronization(21, T, 1, 2))

# print(run_generation(P, T, e, r, k, points, m))

run_GA(r, k, e, np, nt, points, 20, m, termination)
