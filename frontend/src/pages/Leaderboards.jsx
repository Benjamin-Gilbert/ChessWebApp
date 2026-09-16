import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import ProfileMenu from "../components/ProfileMenu";

const MODES = [
  { value: "classic", label: "Classic" },
  { value: "chesspoker", label: "Chess Poker" },
];

const TIME_CONTROLS = [
  { value: "bullet", label: "Bullet" },
  { value: "blitz", label: "Blitz" },
  { value: "rapid", label: "Rapid" },
];

const RATING_TYPES = [
  { value: "rating", label: "Rating" },
  { value: "wins", label: "Most Wins" },
];

export default function Leaderboards() {
  const [mode, setMode] = useState("classic");
  const [timeControl, setTimeControl] = useState("blitz");
  const [ratingType, setRatingType] = useState("rating");
  const [loading, setLoading] = useState(true);
  const [leaders, setLeaders] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/Leaderboard")
    .then(res => res.json())
    .then(data => setLeaders(data))
    .catch(err => console.error("Leaderboard fetch error: ", err))
    .finally(() => setLoading(false));
  }, []);
  console.log("leaders data:", JSON.stringify(leaders));

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
        <div style={styles.panel}>
          <h1 style={styles.heading}>Leaderboards</h1>

          <div style={styles.filters}>
            <div style={styles.filterGroup}>
              <label style={styles.filterLabel}>Mode</label>
              <select
                style={styles.dropDown}
                value={mode}
                onChange={(e) => setMode(e.target.value)}
              >
                {MODES.map((m) => (
                  <option style={styles.option} value={m.value} key={m.value}>
                    {m.label}
                  </option>
                ))}
              </select>
            </div>

            <div style={styles.filterGroup}>
              <label style={styles.filterLabel}>Time Control</label>
              <select
                style={styles.dropDown}
                value={timeControl}
                onChange={(e) => setTimeControl(e.target.value)}
              >
                {TIME_CONTROLS.map((t) => (
                  <option style={styles.option} value={t.value} key={t.value}>
                    {t.label}
                  </option>
                ))}
              </select>
            </div>

            <div style={styles.filterGroup}>
              <label style={styles.filterLabel}>Sort By</label>
              <select
                style={styles.dropDown}
                value={ratingType}
                onChange={(e) => setRatingType(e.target.value)}
              >
                {RATING_TYPES.map((r) => (
                  <option style={styles.option} value={r.value} key={r.value}>
                    {r.label}
                  </option>
                ))}
              </select>
            </div>
          </div>
          

          <table style={styles.table}>
            <thead>
              <tr style={styles.tableHeaderRow}>
                <th style={styles.th}>Rank</th>
                <th style={{ ...styles.th, textAlign: "left" }}>Player</th>
                <th style={styles.th}>Rating</th>
                <th style={styles.th}>W</th>
                <th style={styles.th}>D</th>
                <th style={styles.th}>L</th>
                <th style={styles.th}>GP</th>
              </tr>
              
            </thead>
            <tbody>
              
              {leaders.map((player) => (
                <tr
                  key={player.rank}
                  style={{
                    backgroundColor: player.rank % 2 === 0 ? "#131212" : "#1f1f1f",
                  }}
                >
                  <td style={styles.td}>
                    {player.rank <= 3 ? (
                      <span style={styles.medal}>
                        {player.rank === 1 ? "🥇" : player.rank === 2 ? "🥈" : "🥉"}
                      </span>
                    ) : (
                      player.rank
                    )}
                  </td>
                  <td style={{ ...styles.td, textAlign: "left", fontWeight: "bold" }}>
                    {player.username}
                  </td>
                  <td style={styles.td}>{player.rating}</td>
                  <td style={{ ...styles.td, color: "#7fd17f" }}>{player.gamesWon}</td>
                  <td style={{ ...styles.td, color: "#999" }}>{player.gamesDrawn}</td>
                  <td style={{ ...styles.td, color: "#e37b7b" }}>{player.gamesLost}</td>
                  <td style={styles.td}>{player.gamesPlayed}</td>
                </tr>
              ))}
            </tbody>
          </table>

          {leaders.length === 0 && (
            <p style={styles.dim}>No ranked players yet for this category.</p>
          )}
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
  content: {
    flex: 1,
    display: "flex",
    justifyContent: "center",
    padding: "40px",
  },
  panel: {
    width: "800px",
    backgroundColor: "#5e5454",
    borderRadius: "8px",
    padding: "30px",
    display: "flex",
    flexDirection: "column",
    gap: "24px",
  },
  heading: {
    margin: 0,
    textAlign: "center",
  },
  filters: {
    display: "flex",
    flexDirection: "row",
    gap: "20px",
    justifyContent: "center",
    flexWrap: "wrap",
  },
  filterGroup: {
    display: "flex",
    flexDirection: "column",
    gap: "6px",
  },
  filterLabel: {
    fontSize: "13px",
    color: "#ddd",
  },
  dropDown: {
    height: "44px",
    width: "180px",
    backgroundColor: "#222",
    color: "white",
    border: "none",
    borderRadius: "4px",
  },
  option: {
    color: "white",
    background: "#222",
  },
  table: {
    width: "100%",
    borderCollapse: "collapse",
    backgroundColor: "#1f1f1f",
    borderRadius: "6px",
    overflow: "hidden",
  },
  tableHeaderRow: {
    backgroundColor: "#131212",
  },
  th: {
    padding: "12px 10px",
    fontSize: "13px",
    color: "#aaa",
    textAlign: "center",
    borderBottom: "2px solid #333",
  },
  td: {
    padding: "12px 10px",
    fontSize: "14px",
    textAlign: "center",
  },
  medal: {
    fontSize: "18px",
  },
  dim: {
    color: "#999",
    fontSize: 14,
    textAlign: "center",
  },
};
