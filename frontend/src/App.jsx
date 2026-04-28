import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import ChessBoard from "./components/ChessBoard";

function App() {
  return (
    <div>
      <h1>Chess App</h1>
      <ChessBoard />
    </div>
  );
}

export default App
