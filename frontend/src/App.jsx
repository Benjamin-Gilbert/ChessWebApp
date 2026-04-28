import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import ChessBoard from "./components/ChessBoard";
import ChessGame from "./pages/ChessGame";
import bp from "./assets/pieces/bp.svg";
import bk from "./assets/pieces/bk.svg";

function App() {
  return (
    // <div style={{
    //   display: "flex",
    //   justifyContent: "center",
    //   alignItems: "center",
    //   height: "100vh"
    // }}>
    //   <ChessBoard />
    // </div>
    <ChessGame />
  );
}

export default App
