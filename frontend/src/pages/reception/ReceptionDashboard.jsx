import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import {
  Box,
  Button,
  Card,
  CardContent,
  Chip,
  Grid,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";

import PeopleIcon from "@mui/icons-material/People";
import LocalHospitalIcon from "@mui/icons-material/LocalHospital";
import VisibilityIcon from "@mui/icons-material/Visibility";
import MedicalServicesIcon from "@mui/icons-material/MedicalServices";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import AssignmentIcon from "@mui/icons-material/Assignment";

import { useAuth } from "../../context/AuthContext";

const ReceptionDashboard = () => {
  const navigate = useNavigate();

  const { user } = useAuth();

  const [currentTime, setCurrentTime] = useState(new Date());

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentTime(new Date());
    }, 1000);

    return () => clearInterval(interval);
  }, []);

  const statCards = [
    {
      title: "Total Patients Today",
      value: 0,
      icon: <PeopleIcon color="primary" sx={{ fontSize: 40 }} />,
      borderColor: "#1976d2",
    },
    {
      title: "Active Visits",
      value: 0,
      icon: <LocalHospitalIcon color="success" sx={{ fontSize: 40 }} />,
      borderColor: "#2e7d32",
    },
    {
      title: "Pending OPT",
      value: 0,
      icon: <VisibilityIcon color="warning" sx={{ fontSize: 40 }} />,
      borderColor: "#ed6c02",
    },
    {
      title: "Pending Doctor",
      value: 0,
      icon: <MedicalServicesIcon color="secondary" sx={{ fontSize: 40 }} />,
      borderColor: "#9c27b0",
    },
  ];

  return (
    <Box>

      {/* Header */}

      <Stack
        direction="row"
        justifyContent="space-between"
        alignItems="center"
        sx={{ mb: 4 }}
      >
        <Box>
          <Typography variant="h4">
            Welcome, {user?.fullName}
          </Typography>

          <Typography color="text.secondary">
            {currentTime.toLocaleDateString()} |{" "}
            {currentTime.toLocaleTimeString()}
          </Typography>
        </Box>

        <Chip
          label="RECEPTIONIST"
          color="primary"
        />
      </Stack>

      {/* Statistics */}

      <Grid
        container
        spacing={3}
        sx={{ mb: 4 }}
      >
        {statCards.map((card) => (
          <Grid
            item
            xs={12}
            md={3}
            key={card.title}
          >
            <Card
              elevation={2}
              sx={{
                borderLeft: 6,
                borderColor: card.borderColor,
                borderRadius: 2,
              }}
            >
              <CardContent>

                <Stack
                  direction="row"
                  spacing={2}
                  alignItems="center"
                >
                  {card.icon}

                  <Box>
                    <Typography variant="h4">
                      {card.value}
                    </Typography>

                    <Typography color="text.secondary">
                      {card.title}
                    </Typography>
                  </Box>

                </Stack>

              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>

      {/* Quick Actions */}

      <Card
        elevation={2}
        sx={{
          p: 3,
          borderRadius: 2,
          mb: 4,
        }}
      >
        <Typography
          variant="h6"
          sx={{ mb: 3 }}
        >
          Quick Actions
        </Typography>

        <Stack
          direction={{
            xs: "column",
            md: "row",
          }}
          spacing={2}
        >
          <Button
            variant="contained"
            size="large"
            startIcon={<PersonAddIcon />}
            onClick={() =>
              navigate("/reception/register-patient")
            }
            sx={{
              flex: 1,
              py: 1.5,
            }}
          >
            Register New Patient
          </Button>

          <Button
            variant="contained"
            color="success"
            size="large"
            startIcon={<AssignmentIcon />}
            onClick={() =>
              navigate("/reception/process-visit")
            }
            sx={{
              flex: 1,
              py: 1.5,
            }}
          >
            Process Visit
          </Button>
        </Stack>
      </Card>

      {/* Recent Activity */}

      <Card
        elevation={2}
        sx={{
          borderRadius: 2,
        }}
      >
        <CardContent>

          <Typography
            variant="h6"
            sx={{ mb: 2 }}
          >
            Recent Activity
          </Typography>

          <Table>

            <TableHead>
              <TableRow>
                <TableCell>OPD Number</TableCell>
                <TableCell>Patient Name</TableCell>
                <TableCell>Mobile</TableCell>
                <TableCell>Status</TableCell>
                <TableCell>Visit Date</TableCell>
                <TableCell>Action</TableCell>
              </TableRow>
            </TableHead>

            <TableBody>
              <TableRow>
                <TableCell
                  colSpan={6}
                  align="center"
                >
                  <Typography
                    color="text.secondary"
                    sx={{ py: 4 }}
                  >
                    No recent visits today
                  </Typography>
                </TableCell>
              </TableRow>
            </TableBody>

          </Table>

        </CardContent>
      </Card>

    </Box>
  );
};

export default ReceptionDashboard;