import base64
import time
from typing import Optional, Union
from fastapi import FastAPI, HTTPException, Header
import jwt
from langchain.chat_models.openai import ChatOpenAI
from langchain.schema import SystemMessage, HumanMessage, AIMessage
import os
from dotenv import load_dotenv
from fastapi.middleware.cors import CORSMiddleware
from app.main_router import router
from app.api.common.model.request import Request
from app.api.common.model.response import Response
from icecream import ic

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
load_dotenv(os.path.join(BASE_DIR, ".env"))

app = FastAPI()

origins = ['*']

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(router)

@app.get("/")
async def read_root():
    chat_model = ChatOpenAI(
        openai_api_key=os.environ["API_KEY"],
        temperature=0.1,
        max_tokens=2048,
        model_name="gpt-3.5-turbo-0613",
    )

    message = [
        SystemMessage(content="""
                      You are SQL Developer. My database is innodb mysql.
                      The database has a table named 'players' with the following columns: id, name, age.
                      The database has another table named 'teams' with the following columns: id, name, player_id.
                      """, type="system"),
        HumanMessage(content="What is the SQL query to get all players?", type="human"),
        AIMessage(content="SELECT * FROM players", type="ai"),
    ]

    print('[답변] : ', chat_model.predict_messages(message))
    return {"Hello": "World"}


@app.post("/api/chat/ai")
async def chat(req:Request, Authorization = Header(default=None)) -> Response:
    if Authorization is None:
        raise HTTPException(status_code=401, detail="Authorization Error")
    try:
        token = jwt.decode(Authorization.split(' ')[1], base64.b64decode(os.environ["SECRET_KEY"].encode()), algorithms=['HS256'])
        if token.get('exp') < time.time() or token.get('iss') != os.environ["ISSUER"]:
            raise HTTPException(status_code=401, detail="Authorization Error")
    except Exception as e:
        raise HTTPException(status_code=401, detail="Authorization Error")
    
    chat_model = ChatOpenAI(
        openai_api_key=os.environ["API_KEY"],
        temperature=0.1,
        max_tokens=2048,
        model_name="gpt-3.5-turbo-0613",
    )

    return Response(values=chat_model.predict(req.question))

@app.get("/test")
async def test(Authorization: Optional[str] = Header(None, convert_underscores=False)):
    if Authorization is None:
        raise HTTPException(status_code=401, detail="Authorization Error")
    try:
        token = jwt.decode(Authorization.split(' ')[1], base64.b64decode(os.environ["SECRET_KEY"].encode()), algorithms=['HS256'])
    except Exception as e:
        raise HTTPException(status_code=401, detail="Authorization Error")
    
        
    return {"Hello": "World"}


if __name__ == "__main__":
    import os
    import uvicorn
    # os.chdir(os.getcwd() + '/backend')
    # print(f'{os.getcwd()}')
    uvicorn.run(app, host="localhost", port=8000)