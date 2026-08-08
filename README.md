# URL Shortener

A full-stack URL shortener built with Spring Boot and MySQL. Paste a long URL, get a short one back, and it redirects you to the original — with click tracking and a history of everything you've created.

## Features

- Shorten any valid URL into a compact Base62-encoded short link
- Redirects from the short link to the original URL
- Tracks number of clicks per short link
- View history of all created links, with the ability to delete any of them
- Input validation — rejects malformed URLs with a clear error message

## Tech Stack

- **Backend:** Java, Spring Boot, Spring Data JPA
- **Database:** MySQL
- **Frontend:** Plain HTML, CSS, JavaScript (Fetch API)

## Project Structure

```
urlshortener/
├── url-shortener/            # Spring Boot backend
└── url-shortener-frontend/   # Static HTML/CSS/JS frontend
```

## Setup & Run

### Backend

1. Navigate to the `url-shortener` folder and open it in IntelliJ (or your IDE of choice).
2. Create the database in MySQL:
   ```sql
   CREATE DATABASE url_shortener;
   ```
3. Update `src/main/resources/application.properties` with your own MySQL username/password if different from the defaults.
4. Run `UrlShortenerApplication.java`. The backend starts on **port 9091**.

### Frontend

1. Open the `url-shortener-frontend` folder in VS Code.
2. Right-click `index.html` → **Open with Live Server** (requires the Live Server extension).
3. Make sure the backend is running first — the frontend calls `http://localhost:9091` directly.

> Note: the frontend and backend run as two separate local servers, so CORS is enabled on the backend (`@CrossOrigin`) to allow requests between them.

## API Endpoints

| Method | Endpoint             | Description                          |
|--------|-----------------------|--------------------------------------|
| POST   | `/api/shorten`        | Create a short URL from a long one   |
| GET    | `/{shortCode}`         | Redirect to the original URL         |
| GET    | `/api/urls`            | Get all created URLs                 |
| DELETE | `/api/urls/{id}`       | Delete a URL by its ID               |

## Design Decisions

- **Base62 encoding** for short codes (using the auto-incremented DB id) — keeps short codes compact and collision-free without needing a separate uniqueness check.
- **Click tracking** happens on every redirect, incrementing a counter stored per URL.
- **Separate frontend/backend servers** during development, connected via CORS — kept things simple to set up and debug independently.

## Possible Future Improvements

- Custom aliases for short codes
- Link expiry dates
- Deploy backend and frontend together as a single unit
