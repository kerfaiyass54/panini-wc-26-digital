# Panini Team Generator

This service is responsible for generating virtual teams for the Panini World Cup 2026 digital collection application. It consumes player data from a Kafka topic, stores it in Elasticsearch, and exposes an API to generate teams.

## Tech Stack

*   **Python 3.11**
*   **FastAPI**
*   **Kafka** (for event-driven communication)
*   **Elasticsearch** (for data storage and retrieval)
*   **python-dotenv** (for environment variable management)

## Setup

1.  **Install dependencies:**

    ```bash
    pip install -r requirements.txt
    ```

2.  **Configure environment variables:**

    Create a `.env` file in the root of the `panini-team-generator` directory and add the following variables:

    ```
    KAFKA_BOOTSTRAP_SERVERS=localhost:29092
    ELASTICSEARCH_URL=http://localhost:9200
    CORS_ALLOWED_ORIGINS=http://localhost:4200
    ```

    An example file (`.env.example`) is provided.

## Running the Service

To run the service, use the following command:

```bash
uvicorn app.main:app --reload
```

This will start the FastAPI application and the Kafka consumer in a background thread.