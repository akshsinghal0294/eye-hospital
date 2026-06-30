import { useState } from "react";
import { Link, useLocation } from "react-router-dom";

import {
  AppBar,
  Box,
  Button,
  Chip,
  Drawer,
  IconButton,
  List,
  ListItemButton,
  ListItemText,
  Toolbar,
  Typography,
  useMediaQuery,
  useTheme,
} from "@mui/material";

import LocalHospitalIcon from "@mui/icons-material/LocalHospital";
import MenuIcon from "@mui/icons-material/Menu";

import { useAuth } from "../../context/AuthContext";

const Navbar = () => {
  const { user, logout } = useAuth();

  const location = useLocation();

  const theme = useTheme();

  const isMobile = useMediaQuery(theme.breakpoints.down("md"));

  const [drawerOpen, setDrawerOpen] = useState(false);

  const menuItems = {
    RECEPTIONIST: [
      {
        label: "Dashboard",
        path: "/reception",
      },
      {
        label: "Register Patient",
        path: "/reception/register-patient",
      },
      {
        label: "Process Visit",
        path: "/reception/process-visit",
      },
    ],

    OPTOMETRIST: [
      {
        label: "Add OPT Record",
        path: "/optometrist/add-record",
      },
      {
        label: "View Patients",
        path: "/optometrist/patients",
      },
    ],

    DOCTOR: [
      {
        label: "Add Doctor Record",
        path: "/doctor/add-record",
      },
      {
        label: "View Patients",
        path: "/doctor/patients",
      },
    ],

    WARD_STAFF: [
      {
        label: "Ward Admission",
        path: "/ward/admission",
      },
      {
        label: "Daily Progress",
        path: "/ward/progress",
      },
    ],
  };

  const roleColor = {
    RECEPTIONIST: "primary",
    OPTOMETRIST: "success",
    DOCTOR: "warning",
    WARD_STAFF: "secondary",
  };

  const navigationItems = menuItems[user?.role] || [];

  return (
    <>
      <AppBar
        position="fixed"
        sx={{
          height: 64,
          justifyContent: "center",
        }}
      >
        <Toolbar>

          <Box
            sx={{
              display: "flex",
              alignItems: "center",
              mr: 4,
            }}
          >
            <LocalHospitalIcon sx={{ mr: 1 }} />

            <Typography variant="h6">
              Eye Hospital
            </Typography>
          </Box>

          {!isMobile && (
            <Box
              sx={{
                display: "flex",
                gap: 1,
                flexGrow: 1,
              }}
            >
              {navigationItems.map((item) => (
                <Button
                  key={item.path}
                  component={Link}
                  to={item.path}
                  color="inherit"
                  variant={
                    location.pathname === item.path
                      ? "outlined"
                      : "text"
                  }
                  sx={{
                    textTransform: "none",
                    borderColor: "white",
                    color: "white",
                  }}
                >
                  {item.label}
                </Button>
              ))}
            </Box>
          )}

          {isMobile && (
            <Box sx={{ flexGrow: 1 }}>
              <IconButton
                color="inherit"
                onClick={() => setDrawerOpen(true)}
              >
                <MenuIcon />
              </IconButton>
            </Box>
          )}

          <Box
            sx={{
              display: "flex",
              alignItems: "center",
              gap: 2,
            }}
          >
            <Typography>
              {user?.fullName}
            </Typography>

            <Chip
              label={user?.role}
              color={roleColor[user?.role] || "default"}
            />

            <Button
              color="inherit"
              onClick={logout}
              sx={{
                textTransform: "none",
              }}
            >
              Logout
            </Button>
          </Box>

        </Toolbar>
      </AppBar>

      <Drawer
        anchor="left"
        open={drawerOpen}
        onClose={() => setDrawerOpen(false)}
      >
        <Box sx={{ width: 260 }}>

          <List>

            {navigationItems.map((item) => (
              <ListItemButton
                key={item.path}
                component={Link}
                to={item.path}
                selected={location.pathname === item.path}
                onClick={() => setDrawerOpen(false)}
              >
                <ListItemText primary={item.label} />
              </ListItemButton>
            ))}

          </List>

        </Box>
      </Drawer>
    </>
  );
};

export default Navbar;