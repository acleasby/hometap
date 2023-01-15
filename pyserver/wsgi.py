import json

from flask import Flask, request

app = Flask(__name__)


@app.route('/property/details', methods=['GET'])
def get_object():
    zip=request.args["zipcode"]
    return json.load(open(f"details-{zip}.json"))


if __name__ == '__main__':
    app.run(host='0.0.0.0')
