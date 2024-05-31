from dataclasses import dataclass
from pydantic import BaseModel

class Response(BaseModel):
    status: int = 200
    message: str = "success"
    values: object = None