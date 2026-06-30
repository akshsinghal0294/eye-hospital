import {
    createContext,
    useContext,
    useMemo,
    useState,
  } from "react";
  import { useNavigate } from "react-router-dom";
  
  import authApi from "../api/authApi";
  
  const AuthContext = createContext(null);
  
  export const AuthProvider = ({ children }) => {
    const navigate = useNavigate();
  
    const [user, setUser] = useState(() => {
      const storedUser = localStorage.getItem("user");
      return storedUser ? JSON.parse(storedUser) : null;
    });
  
    const [token, setToken] = useState(() => {
      return localStorage.getItem("token");
    });
  
    const [loading, setLoading] = useState(false);
  
    const login = async (username, password) => {
      setLoading(true);
  
      try {
        const response = await authApi.login(username, password);
  
        const loggedInUser = response.data;
  
        localStorage.setItem("token", loggedInUser.token);
        localStorage.setItem("user", JSON.stringify(loggedInUser));
  
        setToken(loggedInUser.token);
        setUser(loggedInUser);
  
        return loggedInUser.role;
      } finally {
        setLoading(false);
      }
    };
  
    const logout = () => {
      localStorage.removeItem("token");
      localStorage.removeItem("user");
  
      setUser(null);
      setToken(null);
  
      navigate("/login", { replace: true });
    };
  
    const value = useMemo(
      () => ({
        user,
        token,
        loading,
        login,
        logout,
        isAuthenticated: !!token,
      }),
      [user, token, loading]
    );
  
    return (
      <AuthContext.Provider value={value}>
        {children}
      </AuthContext.Provider>
    );
  };
  
  export const useAuth = () => useContext(AuthContext);