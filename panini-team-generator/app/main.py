from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from threading import Thread
from dotenv import load_dotenv
import os

load_dotenv()

from app.elastic.elastic_client import ensure_indices
from app.kafka.owned_players_consumer import start_consumer
from app.routes.team_routes import router as team_router

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=[os.getenv("CORS_ALLOWED_ORIGINS")],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


@app.on_event("startup")
def startup():
    ensure_indices()
    Thread(target=start_consumer, daemon=True).start()


app.include_router(team_router)


@app.get("/")
def health():
    return {"status": "running"}