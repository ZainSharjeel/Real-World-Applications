import csv
import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

users = []


def readData():
    with open('data.csv') as csv_file:
        csv_reader = csv.DictReader(csv_file)

        for row in csv_reader:
            user = {"name": row["name"],
                    "email": row["email"], "type": row["type"]}
            users.append(user)


def readEmailContent():
    content = ""
    with open('content.txt', 'r') as file:
        for line in file:
            content += line

    return content


def sendEmail(content, subject):
    sender = input("Enter email: ")
    my_password = input("Enter password: ")
    for user in users:
        body = ""
        email = user["email"]
        name = user["name"]
        type = user["type"]
        if type == "user":
            body = f'<!DOCTYPE html><html><body>Hi <strong>{name}</strong>,<br><br>{content}></html>'
        else:
            body = f'<!DOCTYPE html><html><body>Hi,<br><br>{content}</html>'

        send(email, body, subject, sender, my_password)


def send(email, msg, subject, sender, my_password):
    
    content = f'<html><body>{msg}</body></html>'
    msg = MIMEMultipart('alternative')
    msg['Subject'] = subject
    msg['From'] = sender
    msg['To'] = email
    part2 = MIMEText(content, 'html')

    msg.attach(part2)

    s = smtplib.SMTP_SSL('smtp.gmail.com')

    s.login(sender, my_password)

    s.sendmail(sender, email, msg.as_string())
    s.quit()


if __name__ == "__main__":
    subject = input("Enter email subject: ")
    readData()
    content = readEmailContent()
    sendEmail(content, subject)
