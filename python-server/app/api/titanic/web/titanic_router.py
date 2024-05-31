import base64
import os
from fastapi import APIRouter, HTTPException, Header

from fastapi.middleware.cors import CORSMiddleware
import jwt
from app.api.common.model.request import Request
from app.api.titanic.service.titanic_service import TitanicService
from app.api.common.model.response import Response

router = APIRouter()

@router.post("/api/chat/titanic")
async def titanic(req:Request, Authorization = Header(default=None)):
    if Authorization is None:
        raise HTTPException(status_code=401, detail="Authorization Error")
    try:
        token = jwt.decode(Authorization.split(' ')[1], base64.b64decode(os.environ["SECRET_KEY"].encode()), algorithms=['HS256'])
    except Exception as e:
        raise HTTPException(status_code=401, detail="Authorization Error")
    service = TitanicService()
    service.preprocess()
    return Response(values=f"The accuracy of {req.question} Titanic model is {service.learning(service.modeling(req.question), req.question)}%")