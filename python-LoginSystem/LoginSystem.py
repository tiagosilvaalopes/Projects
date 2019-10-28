import json
import io
import os

def loadDB(data):
    print("Searching for database.")
    if os.path.isfile(os.getcwd() + "/data.json") and os.access("data.json", os.R_OK):
        print("Database file correctly loaded.")
        with open('data.json', 'r') as fp:
            data = json.load(fp)
    else:
        print("File does not exists, creating a new one now.")
        with io.open(os.path.join(os.getcwd(), "data.json"), 'w') as db_file:
            db_file.write(json.dumps({}))

def saveDB(data):
    with open('data.json', 'w') as fp:
        json.dump(data, fp)

def main():
    tempEmail = ""
    tempPassword = ""
    data = {}
    
    while(True):
        loadDB(data)
        print(data)
        option = input("Do you have an account (Y/N)? ").upper()
        while(not(option == "Y" or option == "N")):
            option = input("Do you have an account (Y/N)? ")

        if(option == 'Y'):
            login() 
        else:
            createAccount(data)

def createAccount(data):
    #ask for email
    tempEmail = input("Email: ")
    #check if this email is already registered
    #ask for password
    tempPassword = input("Password: ")
    #ask again to confirm password
    confimartion = input("Confirm password: ")
    if(not(tempPassword == confimartion)):
        print("Error! Passwords don't match!")
        createAccount()
    else:
        print("You are now registered")
        data[tempEmail] = {tempPassword}
        saveDB(data)
        return

def login():
    #importDB
    return None



# TESTING
main()
