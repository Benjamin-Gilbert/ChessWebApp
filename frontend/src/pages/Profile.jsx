import { Link } from "react-router-dom";

export default function Profile() {
  return (
    <div style={styles.page}>
        <div style = {styles.navBar}>
              <Link to="/" style={styles.titleText}>ChessPoker</Link>
              <Link to="/play" style={styles.links}>Play</Link>
              <Link to="/leaderboards" style={styles.links}>Leaderboards</Link>
              <Link to="/openings" style={styles.links}>Openings</Link>
              <Link to="/about" style={styles.links}>About</Link>
              <div style={styles.profileContainer}>
                <Link to="/profile" style={styles.links}>*Profile</Link>
              </div>
              <h3></h3>
        
              </div>
    </div>
  );
}
const styles = {
    page: {
        height: "100vh",
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
        backgroundColor: "#1f1f1f",
        padding: "0px 0px 10px 0px",
        gap: "75px",
        //justifyContent: "space-between",
    },
    titleText: {
    fontSize: 35,
    color: "inherit",
    textDecoration: "none",
    fontWeight: "bold",
    padding: "20px 0px 0px 100px",
  },
  links: {
    fontSize: 20,
    color: "inherit",
    textDecoration: "none",
    fontWeight: "bold",
    padding: "30px 0px 0px 0px",
    //gap
  },
  profileContainer: {
    display: "flex",
    justifyContent: "right",
    height: "100%",
    width: "100%",
  }
};