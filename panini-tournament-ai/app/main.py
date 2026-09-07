from fastapi import FastAPI
import threading

from app.kafka.consumer import (
    start_consumer
)

app = FastAPI()

@app.on_event("startup")
def startup():

    threading.Thread(
        target=start_consumer,
        daemon=True
    ).start()

@app.get("/")
def health():

    return {
        "status": "running"
    }