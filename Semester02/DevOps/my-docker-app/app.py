from flask import Flask
app = Flask(__name__)

@hello
def hello():
    return "Hello from inside Docker!"

app.route("/")
def hello():
    return "Hello from inside Docker!"

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
'''

And `requirements.txt`:
'''
flask