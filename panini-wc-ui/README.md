# Panini WC UI

This is the frontend application for the Panini World Cup 2026 digital collection application. It is built with Angular and provides the user interface for managing sticker collections, trading with other users, and participating in tournaments.

## Tech Stack

*   **Angular**
*   **TypeScript**
*   **Keycloak-Angular** (for authentication)

## Setup

1.  **Install dependencies:**

    ```bash
    npm install
    ```

2.  **Configure environment:**

    The application uses `environment.ts` and `environment.prod.ts` for configuration. These files contain the Keycloak configuration and the API endpoints for the backend services.

## Running the Application

To run the application in development mode, use the following command:

```bash
npm start
```

This will start a development server on `http://localhost:4200`.

## Building the Application

To build the application for production, use the following command:

```bash
npm run build
```

This will create a `dist` folder with the production-ready application.
