import ChessBoard from "../components/ChessBoard";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";

export default function ChessGame() {
  const { gameID } = useParams();
  const [gameData, setGameData] = useState(null);

  //console.log("RENDER:", { gameID, gameData });

  useEffect(() => {

    if (!gameID) {
      //console.log("NO GAME ID YET");
      return;
    }

    //console.log("USEEFFECT RUNNING:", gameID);

    fetch(`http://localhost:8080/game/${gameID}`)
      .then(res => {
        //console.log("STATUS:", res.status);
        return res.json();
      })
      .then(data => {
        //console.log("RAW DATA:", data);
        setGameData(data);
      })
      .catch(err => {
        console.error("FETCH ERROR:", err);
      });

  }, [gameID]);

  if (!gameData) {
    return <div>Loading...</div>;
  }
  return (
    <div style={styles.page}>
      {/* MAIN AREA */}
      <div style = {styles.navBar}>
      <Link to="/" style={styles.titleText}>ChessPoker</Link>
      <Link to="/play"style={styles.links}>Play</Link>
      <Link to="/leaderboards" style={styles.links}>Leaderboards</Link>
      <Link to="/openings" style={styles.links}>Openings</Link>
      <Link to="/about" style={styles.links}>About</Link>
      <div style={styles.profileContainer}>
        <Link to="/profile" style={styles.links}>*Profile</Link>
      </div>
      <h3></h3>

      </div>
      <div style={styles.main}>
        
        {/*Chat and game functions.*/}
        <div style={styles.chatPanel}>
          <h3>Chat</h3>
          <div style={styles.chatBox}>
            <div>Player A (2052) vs Player B (1500)</div>
            <div>+2 win/-6 draw/-12 loss</div>
          </div>
          <h3>Game</h3>
          <button>Resign</button>
          <button>Offer Draw</button>
        </div>

        {/* CENTER: BOARD */}
        <div style={styles.boardArea}>
          <div style={styles.topBar}>
            <div>    {gameData.playerB} ({gameData.playerBRating})    </div>
          </div>
          <ChessBoard gameID={gameID} />
          <div style = {styles.bottomBar}>
            <div>    {gameData.playerA} ({gameData.playerARating})    </div>
          </div>
        </div>

        {/*moves list and openings maybe?*/}
        <div style={styles.movesPanel}>
          <div style={{ marginBottom: 10, textAlign: "center", fontSize: "18px"}}>Moves</div>
          <table style = {styles.table}>
            <tr style = {{backgroundColor: "#131212"}}>
              <td>1</td>
              <td>e4</td>
              <td>e5</td>
            </tr>
            <tr>
              <td>2</td>
              <td>Nf3</td>
              <td>Nc6</td>
            </tr>
            <tr style = {{backgroundColor: "#131212"}}>
              <td>3</td>
              <td>Bc4</td>
              <td>Nf6</td>
            </tr>
          </table>
          </div>
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

  topBar: {
    height: "60px",
    width: "800px",
    display: "flex",
    flexDirection: "column",
    justifyContent: "left",
    alignItems: "left",
    backgroundColor: "#1f1f1f",
    borderBottom: "3px solid #444",
    marginBottom: "4px",
  },
  bottomBar: {
    height: "60px",
    width: "800px",
    display: "flex",
    flexDirection: "column",
    justifyContent: "left",
    alignItems: "left",    
    backgroundColor: "#1f1f1f",
    borderBottom: "3px solid #444",
    marginTop: "4px",
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

  main: {
    flex: 1,
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    gap: "20px",
  },

  boardArea: {
    display: "flex",
    justifyContent: "left",
    alignItems: "left",
    flexDirection: "column",
  },

  movesPanel: {
    width: "200px",
    height: "500px",
    backgroundColor: "#1f1f1f",
    padding: "10px",
    borderRadius: "8px",
  },
  chatPanel: {
    width: "200px",
    height: "500px",
    backgroundColor: "#1f1f1f",
    padding: "10px",
    borderRadius: "8px",
  },
  chatBox: {
    fontSize: "14px",
    height: "300px",
  },

  table: {
    width: "100%",
    padding: "10px",
    fontSize: "14px",
    borderCollapse: "collapse",
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