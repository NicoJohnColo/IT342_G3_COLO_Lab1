import './App.css';
import { Routes, Route, Link, useNavigate } from 'react-router-dom';
import Register from './components/Register';
import Login from './components/Login';
import Dashboard from './components/Dashboard';
import ProtectedRoute from './components/ProtectedRoute';
import { isAuthenticated, logout } from './utils/auth';

function App() {
  const navigate = useNavigate();
  return (
    <div className="App">
      <nav className="nav">
        <Link to="/">Home</Link>
        {!isAuthenticated() && <Link to="/register">Register</Link>}
        {!isAuthenticated() && <Link to="/login">Login</Link>}
        {isAuthenticated() && <Link to="/dashboard">Dashboard</Link>}
        {isAuthenticated() && <button onClick={() => { logout(); navigate('/login'); }}>Logout</button>}
      </nav>
      <main>
        <Routes>
          <Route path="/" element={<h2>Welcome — use Register or Login</h2>} />
          <Route path="/register" element={<Register />} />
          <Route path="/login" element={<Login />} />
          <Route path="/dashboard" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
