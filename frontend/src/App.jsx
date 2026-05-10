import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import ChessBoard from "./components/ChessBoard";
import ChessGame from "./pages/ChessGame";
import Home from "./pages/Home";
import bp from "./assets/pieces/bp.svg";
import bk from "./assets/pieces/bk.svg";
import { Routes, Route } from "react-router-dom";
import Leaderboards from './pages/Leaderboards';
import Openings from './pages/Openings';
import About from './pages/About';
import Profile from './pages/Profile';

function App() {
  return (
    <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/play" element={<ChessGame />} />
        <Route path="/leaderboards" element={<Leaderboards />} />
        <Route path="/openings" element={<Openings />} />
        <Route path="/about" element={<About />} />
        <Route path="/profile" element={<Profile />} />
    </Routes>
    // <div style={{
    //   display: "flex",
    //   justifyContent: "center",
    //   alignItems: "center",
    //   height: "100vh"
    // }}>
    //   <ChessBoard />
    // </div>
  );
}

export default App
