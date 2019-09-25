import numpy as np
import matplotlib.pyplot as plt
from matplotlib import colors
from collections import Counter


def ternary (n):
    if n == 0:
        return '0'
    nums = []
    while n:
        n, r = divmod(n, 3)
        nums.append(str(r))
    return ''.join(reversed(nums))

def combination_list(neighborhoodSize, rule, k):
    combination = {}
    for i in range(len(rule)):
        if k == 2:
            config = list(bin(i)[2:].zfill(neighborhoodSize))
        else:
            config = list(ternary(i).zfill(neighborhoodSize))

        config = config[::-1
        ]
        configList = list(map(int, config))  # passar de strings para ints
        configList = tuple(configList)  # criar tuplo com os inteiros

        combination[configList] = rule[i]

    return combination

def create_lattice(ls, k):
    if k == 2:
        lattice = np.random.choice([0, 1], size=ls)
    else:
        lattice = np.random.choice([0,1,2], size=ls)

    return lattice

def update_lattice(lattice, phi, r, k, combinations):
    latticeNextT = []
    neighborhood = 2*r + 1

    for i in range(len(lattice)):
        config = []
        fromTo = -r

        if(i < len(lattice) - r):
            for j in range(neighborhood):
                config.append(lattice[i + fromTo])
                fromTo += 1
        else:
            for j in range(neighborhood):
                if(fromTo > 0):
                    config.append(lattice[fromTo - 1])
                else:
                    config.append(lattice[i + fromTo])
                fromTo += 1

        config = tuple(list(map(int, config)))
        
        latticeNextT.append(combinations[config])

    return latticeNextT

def generate_rule(phi, r, k):
    neighborhoodSize = 2*r + 1
    numberOfConfigs = k**neighborhoodSize

    if k == 2:
        phi = bin(phi)[2:].zfill(numberOfConfigs)
    else:
        phi = ternary(phi).zfill(numberOfConfigs)
    
    phi = phi[::-1]

    return phi
    
def run_ca(lattice, phi, r, k):
    matriz = []
    newLattice = []
    newLattice = lattice
    matriz.append(newLattice)

    neighborhoodSize = 2*r + 1
    numberOfConfigs = k**neighborhoodSize

    rule = generate_rule(phi, r , k)

    combinations = combination_list(neighborhoodSize, rule, k)

    for i in range(200):
        newLattice = list(map(int, update_lattice(newLattice, phi, r, k, combinations)))
        matriz.append(newLattice)

    # plot_and_save_ca(matriz, k)

    return matriz

def plot_and_save_ca(spacetime, k) :
    if(k == 2):
        img_color = colors.ListedColormap(['white', 'black'])
    else:
        img_color = colors.ListedColormap(['red', 'green', 'blue'])
    #SAVE TO FILE

    print("...PLOTING...")
    plt.imshow(spacetime, interpolation='nearest', cmap=img_color)
    plt.colorbar()
    plt.show()
    
    print("...SAVING TO MATRIX_PLOT.pdf...")
    spacetime = np.array(spacetime)
    plt.imsave('MATRIX_PLOT.pdf',spacetime, cmap=img_color)
    print("...SUCCESSFULLY SAVED...")


size = 101
# phi = int("0504058605000F77037755877BFFB77F", 16)  
phi = 21
r = 1
k = 2


# lattice = (create_lattice(size, k))
# print(lattice)

# matriz = run_ca(lattice, phi, r, k)



