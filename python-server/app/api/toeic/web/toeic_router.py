from fastapi import APIRouter

from fastapi.middleware.cors import CORSMiddleware
from app.api.common.model.request import Request
from app.api.titanic.service.titanic_service import TitanicService
from app.api.common.model.response import Response
from app.api.toeic.model.toeic_model import ToeicModel

from icecream import ic

import requests

router = APIRouter()

@router.post("/chat/titanic")
async def toeic_router(req:Request):
    model = ToeicModel(dataPath='c:\\Users\\bitcamp\\personal-project\\python-server\\app\\api\\toeic\\data\\', savePath='c:\\Users\\bitcamp\\personal-project\\python-server\\app\\api\\toeic\\save\\')
    test_json = model.preprocess('toeic_test.json', 'result.json')
    token = requests.post("http://localhost:8080/api/user/login", json={"email": "admin", "password": "1234"}).json().get("accessToken")
    for i in test_json.to_dict(orient='records'):
        ic(i)
        requests.post("http://localhost:8080/api/toeic/register", json=i, headers={"Authorization": f"Bearer {token}"})

    # service.preprocess()
    return Response(answer="")