import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import ProfileMenu from "../components/ProfileMenu";
import { useAuth } from "../context/AuthContext";

const API_BASE = "http://localhost:8080";

export default function Home() {
  const navigate = useNavigate();
  const {token} = useAuth();
  const {username} = useAuth();
  // --- form state (was previously read from raw DOM values / unused) ---
  const [gameMode, setGameMode] = useState("classic");
  const [minutes, setMinutes] = useState("10");
  const [seconds, setSeconds] = useState("0");
  const [color, setColor] = useState("random");
  const [openGames, setOpenGames] = useState([]);
  const [loadingGames, setLoadingGames] = useState(true);
  const [joiningId, setJoiningId] = useState(null);

  const [error, setError] = useState(null);

  async function createGame() {
    try {
      const response = await fetch(`${API_BASE}/game/create`, {
        method: "POST",
        headers: { 
          "Content-Type": "application/json", 
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify({
          gameMode: gameMode,
          minutes: Number(minutes),
          seconds: Number(seconds),
          color: color,
        }),
      });

      if (!response.ok) throw new Error(`Server returned ${response.status}`);
      const data = await response.json();
      navigate(`/play/${data.gameID}`);
    } catch (err) {
      console.error(err);
      setError("Couldn't create the game. Try again.");
    }
  }
  useEffect(() => {
    if(!token){return;}
    let cancelled = false;

    async function fetchOpenGames(){
      try{
        const response = await fetch(`${API_BASE}/game/open`,{
          headers: {"Authorization": `Bearer ${token}`},
        });
        if (!response.ok) throw new Error(`Server returned ${response.status}`);
      const data = await response.json();
      if (!cancelled) {
        setOpenGames(data.filter((g) => g.playerWhite !== username));
        setError(null);
      }
    } catch (err) {
      if (!cancelled) setError("Couldn't load open games.");
      console.error(err);
    } finally {
      if (!cancelled) setLoadingGames(false);
    }
  }

  fetchOpenGames();
  const interval = setInterval(fetchOpenGames, 3000);
  return () => {
    cancelled = true;
    clearInterval(interval);
  };
}, [token, username]);

  async function joinGame(gameID) {
    setJoiningId(gameID);
    try {
      const response = await fetch(`${API_BASE}/game/join/${gameID}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      });
      if (!response.ok) throw new Error(`Server returned ${response.status}`);
      navigate(`/play/${gameID}`);
    } catch (err) {
      console.error(err);
      setError("Couldn't join that game — it may have just filled up.");
      setJoiningId(null);
    }
  }

  return (
    <div style={styles.page}>
      <div style={styles.navBar}>
        <Link to="/" style={styles.titleText}>ChessPoker</Link>
        <Link to="/play" style={styles.links}>Play</Link>
        <Link to="/leaderboards" style={styles.links}>Leaderboards</Link>
        <Link to="/openings" style={styles.links}>Openings</Link>
        <Link to="/about" style={styles.links}>About</Link>
        <ProfileMenu />
      </div>

      <div style={styles.content}>
        <div style={styles.games}>
          <h1>Welcome to Chess Poker! Start a game:</h1>

          <select
            style={styles.dropDown}
            value={gameMode}
            onChange={(e) => setGameMode(e.target.value)}
          >
            <option style={styles.option} value="classic">Classic</option>
            <option style={styles.option} value="ChessPoker">Chess poker</option>
          </select>

          <div style={styles.time}>
            <select
              style={styles.dropDown}
              value={minutes}
              onChange={(e) => setMinutes(e.target.value)}
            >
              {["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"].map((m) => (
                <option style={styles.option} value={m} key={m}>{m}</option>
              ))}
            </select>

            <select
              style={styles.dropDown}
              value={seconds}
              onChange={(e) => setSeconds(e.target.value)}
            >
              {["0", "1", "5", "10", "15", "30"].map((s) => (
                <option style={styles.option} value={s} key={s}>{s}</option>
              ))}
            </select>

            <select
              style={styles.dropDown}
              value={color}
              onChange={(e) => setColor(e.target.value)}
            >
              <option style={styles.option} value="white">white</option>
              <option style={styles.option} value="black">black</option>
              <option style={styles.option} value="random">random</option>
            </select>
          </div>

          <button style={styles.button} onClick={createGame}>start game</button>

          {error && <p style={styles.error}>{error}</p>}
        </div>

        <div style={styles.openGamesPanel}>
          <h2 style={styles.panelTitle}>Open Games</h2>

          {loadingGames && <p style={styles.dim}>Loading games...</p>}
          {!loadingGames && openGames.length === 0 && (
            <p style={styles.dim}>No open games right now — start one!</p>
          )}

          <div style={styles.gameList}>
            {openGames.map((game) => (
              <div style={styles.gameCard} key={game.gameID}>
                <div>
                  <div style={styles.gameCardHost}>{game.playerWhite}({game.playerWhiteRating})</div>
                  <div style={styles.dim}>
                    {game.gameMode} · {game.minutes}+{game.seconds} · {game.color}
                  </div>
                </div>
                <button
                  style={styles.joinButton}
                  onClick={() => joinGame(game.gameID)}
                disabled={joiningId === game.gameID}
                >
                {joiningId === game.gameID ? "Joining..." : "Join"}
                </button>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

const styles = {
  page: {
    minHeight: "100vh",
    display: "flex",
    flexDirection: "column",
    backgroundColor: "#131212",
    color: "white",
  },
  navBar: {
    height: "60px",
    width: "100%",
    display: "flex",
    flexDirection: "row",
    alignItems: "center",
    backgroundColor: "#1f1f1f",
    gap: "40px",
    paddingLeft: "40px",
  },
  content: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "center",
    gap: "40px",
    padding: "40px",
    flexWrap: "wrap",
  },
  games: {
    display: "flex",
    flexDirection: "column",
    width: "500px",
    minHeight: "400px",
    backgroundColor: "#5e5454",
    alignItems: "center",
    gap: "30px",
    padding: "30px",
    borderRadius: "8px",
  },
  time: {
    display: "flex",
    flexDirection: "row",
    gap: "15px",
    justifyContent: "center",
  },
  dropDown: {
    height: "44px",
    width: "150px",
    backgroundColor: "#222",
    color: "white",
    border: "none",
    borderRadius: "4px",
  },
  button: {
    height: "50px",
    width: "200px",
    backgroundColor: "#3a7d44",
    fontSize: 18,
    color: "white",
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
  },
  option: {
    color: "white",
    background: "#222",
  },
  titleText: {
    fontSize: 28,
    color: "inherit",
    textDecoration: "none",
    fontWeight: "bold",
  },
  links: {
    fontSize: 16,
    color: "inherit",
    textDecoration: "none",
    fontWeight: "bold",
  },
  profileContainer: {
    marginLeft: "auto",
  },
  openGamesPanel: {
    width: "400px",
    minHeight: "400px",
    backgroundColor: "#1f1f1f",
    borderRadius: "8px",
    padding: "20px",
  },
  panelTitle: {
    marginTop: 0,
    marginBottom: "16px",
  },
  gameList: {
    display: "flex",
    flexDirection: "column",
    gap: "10px",
  },
  gameCard: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    backgroundColor: "#2b2b2b",
    borderRadius: "6px",
    padding: "12px 16px",
  },
  gameCardHost: {
    fontWeight: "bold",
  },
  joinButton: {
    backgroundColor: "#3a7d44",
    color: "white",
    border: "none",
    borderRadius: "4px",
    padding: "8px 16px",
    cursor: "pointer",
  },
  dim: {
    color: "#999",
    fontSize: 14,
  },
  error: {
    color: "#ff6b6b",
    fontSize: 14,
  },
};
