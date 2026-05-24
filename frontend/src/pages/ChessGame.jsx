import ChessBoard from "../components/ChessBoard";
import { Link } from "react-router-dom";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";

export default function ChessGame() {
  const { gameID } = useParams();
  const [gameData, setGameData] = useState(null);
  const [moveData, setMoveData] = useState([]);
  const [rows, setRows] = useState([]);
  const [turn, setTurn] = useState("white");
  const [gameOverMessage, setGameOverMessage] = useState(null);
  const [showResignPopup, setShowResignPopup] = useState(false);
  const [showGameOverPopup, setShowGameOverPopup] = useState(false);
  const [gameOver, setGameOver] = useState(false);

  function resign(turn){
    setGameOver(true);
    if(turn === "white"){
      setGameOverMessage("White resigns, black wins.");
fetch(
"http://localhost:8080/game/resign",
{
method:"POST",

headers:{
"Content-Type":
"application/json"
},

body: JSON.stringify({
gameID
})
}
)

.then(res => res.json())

.then(data => {

setShowResignPopup(false);
setShowGameOverPopup(true);


    })
  .catch(console.error)
}
    else if(turn === "black"){
      setGameOverMessage("Black resigns, white wins.");
      fetch(
"http://localhost:8080/game/resign",
{
method:"POST",

headers:{
"Content-Type":
"application/json"
},

body: JSON.stringify({
gameID
})
}
)

.then(res => res.json())

.then(data => {

setShowResignPopup(false);
setShowGameOverPopup(true);


    })
  .catch(console.error)
    }
  }

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
            <div>{gameData.playerA} ({gameData.playerARating}) vs {gameData.playerB} ({gameData.playerBRating})</div>
            <div>+12 win/+6 draw/-2 loss</div>
          </div>
          <h3>Game</h3>
          <button style={{width: "100px"}} onClick={() => setShowResignPopup(true)}>Resign</button>
          <button style={{width: "100px"}}>Offer Draw</button>
        </div>

        {/* CENTER: BOARD */}
        <div style={styles.boardArea}>
          <div style={styles.topBar}>
            <div>    {gameData.playerB} ({gameData.playerBRating})    </div>
          </div>
          <ChessBoard gameID={gameID} onGameUpdate={setMoveData} gameOver={gameOver} />
          <div style = {styles.bottomBar}>
            <div>    {gameData.playerA} ({gameData.playerARating})    </div>
          </div>
        </div>

        {/*moves list and openings maybe?*/}
        <div style={styles.movesPanel}>
          <div style={{ marginBottom: 10, textAlign: "center", fontSize: "18px"}}>Moves</div>
          <table style = {styles.table}>
            <tbody>
              {
                moveData?.moveList?.map(move => (
                  <tr key = {move.moveNum} style={{backgroundColor:
                    move.moveNum % 2 === 0
                    ? "#131212"
                    : "#1f1f1f"
                  }}>
                    <td>{move.moveNum}</td>
                    <td>{move.white}</td>
                    <td>{move.black}</td>
                  </tr>
                ))
              }
            </tbody>
          </table>
          </div>
        </div>
              {showResignPopup && (

<div
style={styles.popupBackground}
>

<div
style={styles.popup}
>

<h3>Resign?</h3>

<p>
This game will be counted as a loss.
</p>

<div
style={{
display:"flex",
gap:"10px"
}}
>

<button
onClick={() =>
setShowResignPopup(false)
}
>
Cancel
</button>

<button
onClick={() => resign(moveData.turn)}
>
Confirm
</button>

</div>

</div>

</div>

)}
{showGameOverPopup && (

<div
style={styles.popupBackground}
>

<div
style={styles.popup}
>

<h2>Game Over</h2>

<div
style={{
fontSize:"20px"
}}
>

{gameOverMessage}

</div>

<button
onClick={() =>
setShowGameOverPopup(false)
}
>
Close

</button>

</div>

</div>

)}
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
  },
  popupBackground: {
position:"fixed",

top:0,
left:0,

width:"100%",
height:"100%",

backgroundColor:
"rgba(0,0,0,.5)",

display:"flex",

justifyContent:
"center",

alignItems:
"center",

zIndex:1000
},

popup: {
backgroundColor:"#1f1f1f",

padding:"30px",

borderRadius:"12px",

display:"flex",

flexDirection:"column",

gap:"15px",

color:"white",

minWidth:"300px"
},
};