import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

export default function Home() {
    const navigate = useNavigate();
    async function createGame() {
      const response = await fetch("http://localhost:8080/game/create", {method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
          playerA: "GM Ben",
          playerB: "dumb clanker",
          playerARating: "2800",
          playerBRating: "3600"
        }),
      });

      const data = await response.json();
      console.log(data);
      navigate(`/play/${data.gameID}`);
}
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
        <div style={styles.games}>
          <h1>Welcome to chess poker! start a game:</h1>
          <select style={styles.dropDown} name="gameMode" id="gameMode">
            <option style={styles.option} value="classic">Classic</option>
            <option style={styles.option}value="ChessPoker">Chess poker</option>
          </select>
          <div style={styles.time}>
            <select style={styles.dropDown} name="minutes" id="minutes">
              <option style={styles.option} value="1">1</option>
              <option style={styles.option} value="2">2</option>
              <option style={styles.option} value="3">3</option>
              <option style={styles.option} value="4">4</option>
              <option style={styles.option} value="5">5</option>
              <option style={styles.option} value="6">6</option>
              <option style={styles.option} value="7">7</option>
              <option style={styles.option} value="8">8</option>
              <option style={styles.option} value="9">9</option>
              <option style={styles.option} value="10" selected>10</option>
            </select>
            <select style={styles.dropDown} name="seconds" id="seconds">
              <option style={styles.option} value="0">0</option>
              <option style={styles.option} value="1">1</option>
              <option style={styles.option} value="5">5</option>
              <option style={styles.option} value="10">10</option>
              <option style={styles.option} value="15">15</option>
              <option style={styles.option} value="30">30</option>
            </select>
            <select style={styles.dropDown} name="color" id="color">
              <option style={styles.option} value="white">white</option>
              <option style={styles.option} value="black">black</option>
              <option style={styles.option} value="random" selected>random</option>
            </select>
          </div>
          <button style={styles.button} onClick={createGame}>start game</button>
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
    games: {
      display: "flex",
      flexDirection: "column",
      width: "800px",
      height: "500px",
      backgroundColor: "#5e5454",
      marginLeft: "550px",
      alignItems: "center",
      gap: "40px",
    },
    time: {
      display: "flex",
      flexDirection: "row",
      width: "800px",
      height: "50px",
      gap: "15px",
      justifyContent: "center",
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
    dropDown: {
      height: "50px",
      width: "200px",
      backgroundColor: "#222",
      color: "white",


    },
    button: {
      height: "50px",
      width: "200px",
      backgroundColor: "#222",
      fontSize: 20,
      display: "flex",
      color: "white",
      alignItems: "center",
      justifyContent: "center",
    },

    option: {
      color: "white",
      background: "#222",
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