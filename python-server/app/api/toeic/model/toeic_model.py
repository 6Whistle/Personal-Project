import json
import os, sys

import requests
sys.path.append("c:\\Users\\bitcamp\\personal-project\\python-server")

import pandas as pd
from app.api.context.datasets import Datasets
from app.api.context.models import Models
from icecream import ic
import PyPDF2 as pypdf2
import re


class ToeicModel:

    def __init__(self, dataPath, savePath):
        self.model = Models(dataPath, savePath)
        self.dataset = Datasets()

    def preprocess(self, train_fname, test_fname) -> Datasets:
        that = self.model
        this = self.dataset

        # this.train = pd.read_feather(f"{that.dataset.dname}{train_fname}")
        
        # temp = pypdf2.PdfFileReader(f"{that.dataset.dname}{train_fname}")
        # text = re.sub("[0-9]+.", "\n\n", temp.getPage(1).extractText())
        # text = re.sub('[^a-zA-Z0-9\n\.\(\)\-]', ' ', text)
        # text = re.sub('\n+', '\n', text)
        # text = re.sub('\([A-D]\) [A-Za-z]+ ', ',', text)
        # text = re.sub('[\s]+', ' ', text)

        # print(text)

        temp = json.loads(open(f"{that.dataset.dname}{train_fname}").read())
        temp = [y for x, y in temp.items()]
        temp = pd.DataFrame(temp)
        temp = temp.rename(columns= {'anwser': 'answer'})
        temp['choices'] = temp['1'] + ',' + temp['2'] + ',' + temp['3'] + ',' + temp['4']
        temp.drop(['1', '2', '3', '4'], axis=1, inplace=True)
        temp['part'] = 7

        pd.DataFrame.to_json(temp, f"{that.dataset.sname}{test_fname}", orient='records')
        
        # ic(this.train.head(5))

        return temp

if __name__ == '__main__':
    pass
    toeic = ToeicModel(dataPath='c:\\Users\\bitcamp\\personal-project\\python-server\\app\\api\\toeic\\data\\', savePath='c:\\Users\\bitcamp\\personal-project\\python-server\\app\\api\\toeic\\save\\')
    test_json = toeic.preprocess('toeic_test.json', 'result.json')
    token = requests.post(url="http://localhost:8080/api/user/login", json={"email": "admin", "password": "1234"}).json().get("accessToken")
    ic(test_json.to_dict(orient='records')[0])
    ic(token)
    for i in test_json.to_dict(orient='records'):
        response = requests.post("http://localhost:8080/api/toeic/register", json=i, headers={"Authorization": f"Bearer {token}"})