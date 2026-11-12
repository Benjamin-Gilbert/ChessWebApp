import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from "recharts";
import { useAuth } from "../context/AuthContext";
import ProfileMenu from "../components/ProfileMenu";

const API_BASE = "http://localhost:8080";

export default function Profile() {
  const { token, username } = useAuth();

  const [mode, setMode] = useState("chess"); // "chess" | "chesspoker"
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!token || mode !== "chess") return; // chess-poker has no backend yet — don't fetch

    let cancelled = false;
    setLoading(true);
    setError(null);

    fetch(`${API_BASE}/profile/stats?mode=${mode}`, {
      headers: { "Authorization": `Bearer ${token}` },
    })
      .then(res => {
        if (!res.ok) throw new Error(`Server returned ${res.status}`);
        return res.json();
      })
      .then(data => {
        if (!cancelled) setStats(data);
      })
      .catch(err => {
        console.error("Profile fetch error:", err);
        if (!cancelled) setError("Couldn't load profile stats.");
      })
      .finally(() => {
        if (!cancelled) setLoading(false);
      });

    return () => { cancelled = true; };
  }, [token, mode]);

  const gamesPlayed = stats?.gamesPlayed ?? 0;
  const wins = stats?.wins ?? 0;
  const losses = stats?.losses ?? 0;
  const draws = stats?.draws ?? 0;

  const winPct = gamesPlayed ? ((wins / gamesPlayed) * 100).toFixed(1) : "0.0";
  const lossPct = gamesPlayed ? ((losses / gamesPlayed) * 100).toFixed(1) : "0.0";
  const drawPct = gamesPlayed ? ((draws / gamesPlayed) * 100).toFixed(1) : "0.0";

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
        <div style={styles.header}>
          <div>
            <h1 style={styles.username}>{stats?.username ?? username}</h1>
            <div style={styles.ratingLarge}>{loading ? "—" : stats?.rating ?? "Unrated"}</div>
            <div style={styles.ratingLabel}>Current Rating</div>
          </div>

          <div style={styles.modeToggle}>
            <button
              style={{ ...styles.modeButton, ...(mode === "chess" ? styles.modeButtonActive : {}) }}
              onClick={() => setMode("chess")}
            >
              Chess
            </button>
            <button
              style={{ ...styles.modeButton, ...styles.modeButtonDisabled }}
              onClick={() => {}}
              disabled
              title="Coming soon"
            >
              Chess-Poker
            </button>
          </div>
        </div>

        {mode === "chesspoker" ? (
          <div style={styles.card}>
            <p style={styles.dim}>Chess-Poker stats aren't tracked yet — coming soon.</p>
          </div>
        ) : (
          <>
            {error && <p style={styles.error}>{error}</p>}
            {loading && <p style={styles.dim}>Loading stats...</p>}

            {!loading && !error && (
              <>
                <div style={styles.statsRow}>
                  <div style={styles.statCard}>
                    <div style={styles.statValue}>{gamesPlayed}</div>
                    <div style={styles.statLabel}>Games Played</div>
                  </div>
                  <div style={styles.statCard}>
                    <div style={{ ...styles.statValue, color: "#7fd17f" }}>{wins}</div>
                    <div style={styles.statLabel}>Wins ({winPct}%)</div>
                  </div>
                  <div style={styles.statCard}>
                    <div style={{ ...styles.statValue, color: "#e37b7b" }}>{losses}</div>
                    <div style={styles.statLabel}>Losses ({lossPct}%)</div>
                  </div>
                  <div style={styles.statCard}>
                    <div style={{ ...styles.statValue, color: "#999" }}>{draws}</div>
                    <div style={styles.statLabel}>Draws ({drawPct}%)</div>
                  </div>
                </div>

                <div style={styles.card}>
                  <h2 style={styles.cardTitle}>Rating Over Time</h2>
                  {stats?.ratingHistory && stats.ratingHistory.length > 0 ? (
                    <div style={{ width: "100%", height: 300 }}>
                      <ResponsiveContainer>
                        <LineChart data={stats.ratingHistory}>
                          <CartesianGrid strokeDasharray="3 3" stroke="#333" />
                          <XAxis
                            dataKey="gameNumber"
                            stroke="#aaa"
                            label={{ value: "Game #", position: "insideBottom", offset: -5, fill: "#aaa" }}
                          />
                          <YAxis stroke="#aaa" domain={["dataMin - 50", "dataMax + 50"]} />
                          <Tooltip
                            contentStyle={{ backgroundColor: "#1f1f1f", border: "1px solid #333", color: "white" }}
                          />
                          <Line
                            type="monotone"
                            dataKey="rating"
                            stroke="#3a7d44"
                            strokeWidth={2}
                            dot={{ fill: "#3a7d44", r: 3 }}
                          />
                        </LineChart>
                      </ResponsiveContainer>
                    </div>
                  ) : (
                    <p style={styles.dim}>No rated games played yet.</p>
                  )}
                </div>
              </>
            )}
          </>
        )}
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
  content: {
    maxWidth: "900px",
    width: "100%",
    margin: "0 auto",
    padding: "40px 20px",
    display: "flex",
    flexDirection: "column",
    gap: "24px",
  },
  header: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "flex-start",
    flexWrap: "wrap",
    gap: "16px",
  },
  username: {
    margin: 0,
    fontSize: "28px",
  },
  ratingLarge: {
    fontSize: "48px",
    fontWeight: "bold",
    lineHeight: 1,
    marginTop: "8px",
  },
  ratingLabel: {
    fontSize: "13px",
    color: "#999",
    marginTop: "4px",
  },
  modeToggle: {
    display: "flex",
    gap: "8px",
  },
  modeButton: {
    padding: "10px 20px",
    backgroundColor: "#1f1f1f",
    color: "white",
    border: "1px solid #333",
    borderRadius: "6px",
    fontSize: "14px",
    fontWeight: "bold",
    cursor: "pointer",
  },
  modeButtonActive: {
    backgroundColor: "#3a7d44",
    borderColor: "#3a7d44",
  },
  modeButtonDisabled: {
    opacity: 0.4,
    cursor: "not-allowed",
  },
  statsRow: {
    display: "flex",
    flexDirection: "row",
    gap: "16px",
    flexWrap: "wrap",
  },
  statCard: {
    flex: "1 1 180px",
    backgroundColor: "#1f1f1f",
    borderRadius: "8px",
    padding: "20px",
    textAlign: "center",
  },
  statValue: {
    fontSize: "32px",
    fontWeight: "bold",
  },
  statLabel: {
    fontSize: "13px",
    color: "#999",
    marginTop: "6px",
  },
  card: {
    backgroundColor: "#1f1f1f",
    borderRadius: "8px",
    padding: "24px",
  },
  cardTitle: {
    margin: "0 0 16px 0",
    fontSize: "18px",
  },
  dim: {
    color: "#999",
    fontSize: "14px",
  },
  error: {
    color: "#ff6b6b",
    fontSize: "14px",
  },
};

// TODO: add game recording and displaying recent games on profile page. Then, deploy.
//TODO: update end of game functions to update rating history of players and add game to record.