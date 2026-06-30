import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";

import {
  Alert,
  Box,
  Button,
  Card,
  CardContent,
  CircularProgress,
  Divider,
  TextField,
  Typography,
} from "@mui/material";

import LocalHospitalIcon from "@mui/icons-material/LocalHospital";

import { useAuth } from "../../context/AuthContext";

const Login = () => {
  const navigate = useNavigate();

  const { login } = useAuth();

  const [loading, setLoading] = useState(false);

  const [error, setError] = useState("");

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const onSubmit = async (data) => {
    setLoading(true);
    setError("");

    try {
      const role = await login(
        data.username,
        data.password
      );

      switch (role) {
        case "RECEPTIONIST":
          navigate("/reception");
          break;

        case "OPTOMETRIST":
          navigate("/optometrist");
          break;

        case "DOCTOR":
          navigate("/doctor");
          break;

        case "WARD_STAFF":
          navigate("/ward");
          break;

        default:
          navigate("/unauthorized");
      }
    } catch (err) {
      setError(
        err.response?.data?.message ??
          "Invalid username or password."
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box
      sx={{
        minHeight: "100vh",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        bgcolor: "grey.100",
        p: 2,
      }}
    >
      <Card
        elevation={2}
        sx={{
          width: 420,
          borderRadius: 2,
        }}
      >
        <CardContent sx={{ p: 4 }}>

          <Box
            sx={{
              display: "flex",
              justifyContent: "center",
              mb: 2,
            }}
          >
            <LocalHospitalIcon
              color="primary"
              sx={{ fontSize: 60 }}
            />
          </Box>

          <Typography
            variant="h5"
            align="center"
            fontWeight={600}
          >
            Eye Hospital
          </Typography>

          <Typography
            variant="body2"
            color="text.secondary"
            align="center"
            sx={{ mb: 2 }}
          >
            Management System
          </Typography>

          <Divider sx={{ mb: 3 }} />

          <Box
            component="form"
            onSubmit={handleSubmit(onSubmit)}
          >
            <TextField
              label="Username"
              fullWidth
              margin="normal"
              error={!!errors.username}
              helperText={errors.username?.message}
              {...register("username", {
                required: "Username is required",
                minLength: {
                  value: 3,
                  message:
                    "Minimum 3 characters required",
                },
              })}
            />

            <TextField
              label="Password"
              type="password"
              fullWidth
              margin="normal"
              error={!!errors.password}
              helperText={errors.password?.message}
              {...register("password", {
                required: "Password is required",
                minLength: {
                  value: 4,
                  message:
                    "Minimum 4 characters required",
                },
              })}
            />

            <Button
              type="submit"
              variant="contained"
              fullWidth
              disabled={loading}
              sx={{
                mt: 3,
                textTransform: "none",
              }}
            >
              {loading ? (
                <CircularProgress
                  size={24}
                  color="inherit"
                />
              ) : (
                "Login"
              )}
            </Button>

            {error && (
              <Alert
                severity="error"
                sx={{ mt: 2 }}
              >
                {error}
              </Alert>
            )}
          </Box>

        </CardContent>
      </Card>
    </Box>
  );
};

export default Login;