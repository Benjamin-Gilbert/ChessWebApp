import { useState } from "react";
import { Link } from "react-router-dom";
import ProfileMenu from "../components/ProfileMenu";

// TODO: replace with a real fetch once the backend leaderboard endpoint
// is settled, e.g.:
//   GET http://localhost:8080/leaderboard?mode=classic&timeControl=blitz
// and re-run it in a useEffect whenever `mode` / `timeControl` change.
const SAMPLE_LEADERS = [
  { rank: 1, name: "GM Ben", rating: 2800, wins: 142, losses: 12, draws: 9 },
  { rank: 2, name: "dumb clanker", rating: 2650, wins: 98, losses: 20, draws: 14 },
  { rank: 3, name: "queenSac4life", rating: 2510, wins: 87, losses: 30, draws: 11 },
  { rank: 4, name: "rookLift99", rating: 2440, wins: 75, losses: 28, draws: 8 },
  { rank: 5, name: "e4orDie", rating: 2390, wins: 61, losses: 25, draws: 6 },
];

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

  // Once wired to the backend, swap this for real fetched data
  // (filtered/sorted server-side by mode/timeControl/ratingType).
  const leaders = SAMPLE_LEADERS;

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
                <th style={styles.th}>L</th>
                <th style={styles.th}>D</th>
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
                    {player.name}
                  </td>
                  <td style={styles.td}>{player.rating}</td>
                  <td style={{ ...styles.td, color: "#7fd17f" }}>{player.wins}</td>
                  <td style={{ ...styles.td, color: "#e37b7b" }}>{player.losses}</td>
                  <td style={{ ...styles.td, color: "#999" }}>{player.draws}</td>
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
