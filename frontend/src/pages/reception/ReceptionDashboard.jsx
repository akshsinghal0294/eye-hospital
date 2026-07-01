import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import {
  Box,
  Button,
  Card,
  CardContent,
  Chip,
  CircularProgress,
  Grid,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow,
  TextField,
  Typography,
  Paper,
} from "@mui/material";

import PeopleIcon from "@mui/icons-material/People";
import LocalHospitalIcon from "@mui/icons-material/LocalHospital";
import VisibilityIcon from "@mui/icons-material/Visibility";
import MedicalServicesIcon from "@mui/icons-material/MedicalServices";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import AssignmentIcon from "@mui/icons-material/Assignment";
import { getAllPatients } from "../../api/patientApi";
import { useAuth } from "../../context/AuthContext";

const ReceptionDashboard = () => {

  const navigate = useNavigate();
  const { user } = useAuth();

  const [currentTime, setCurrentTime] = useState(new Date());

  const [patients, setPatients] = useState([]);

  const [loading, setLoading] = useState(false);

  const [page, setPage] = useState(0);

  const [rowsPerPage, setRowsPerPage] = useState(10);

  const [totalRows, setTotalRows] = useState(0);

  const [fromDate, setFromDate] = useState("");

  const [toDate, setToDate] = useState("");

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

  const loadPatients = async () => {

    try {

      setLoading(true);

      const response = await getAllPatients(
        page,
        rowsPerPage,
        fromDate,
        toDate
    );

    setPatients(response.data.content);

    setTotalRows(response.data.totalElements);

    } catch (error) {

      console.error(error);

    } finally {

      setLoading(false);

    }

  };

  useEffect(() => {

    loadPatients();

  }, [page, rowsPerPage]);

  useEffect(() => {

    const interval = setInterval(() => {

      setCurrentTime(new Date());

    }, 1000);

    return () => clearInterval(interval);

  }, []);

  const handleSearch = () => {

    setPage(0);

    loadPatients();

  };

  const handleChangePage = (event, newPage) => {

    setPage(newPage);

  };

  const handleChangeRowsPerPage = (event) => {

    setRowsPerPage(parseInt(event.target.value, 10));

    setPage(0);

  };
  return (
    <Box p={3}>

      {/* Header */}

      <Stack
        direction="row"
        justifyContent="space-between"
        alignItems="center"
        mb={4}
      >

        <Box>

          <Typography
            variant="h4"
            fontWeight="bold"
          >
            Welcome, {user?.fullName}
          </Typography>

          <Typography
            color="text.secondary"
          >
            {currentTime.toLocaleDateString()} |{" "}
            {currentTime.toLocaleTimeString()}
          </Typography>

        </Box>

        <Chip
          label="RECEPTIONIST"
          color="primary"
          sx={{
            fontWeight: "bold",
            px: 2
          }}
        />

      </Stack>

      {/* Statistics */}

      <Grid
        container
        spacing={3}
        mb={4}
      >

        {statCards.map((card) => (

          <Grid
            item
            xs={12}
            sm={6}
            md={3}
            key={card.title}
          >

            <Card
              elevation={3}
              sx={{
                borderLeft: 6,
                borderColor: card.borderColor,
                borderRadius: 3
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

                    <Typography
                      variant="h4"
                      fontWeight="bold"
                    >
                      {card.value}
                    </Typography>

                    <Typography
                      color="text.secondary"
                    >
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
        elevation={3}
        sx={{
          borderRadius: 3,
          mb: 4
        }}
      >

        <CardContent>

          <Typography
            variant="h6"
            mb={3}
            fontWeight="bold"
          >
            Quick Actions
          </Typography>

          <Stack
            direction={{
              xs: "column",
              md: "row"
            }}
            spacing={2}
          >

            <Button
              fullWidth
              variant="contained"
              size="large"
              startIcon={<PersonAddIcon />}
              sx={{ py: 1.5 }}
              onClick={() =>
                navigate("/reception/register-patient")
              }
            >
              Register New Patient
            </Button>

            <Button
              fullWidth
              color="success"
              variant="contained"
              size="large"
              startIcon={<AssignmentIcon />}
              sx={{ py: 1.5 }}
              onClick={() =>
                navigate("/reception/process-visit")
              }
            >
              Process Visit
            </Button>

          </Stack>

        </CardContent>

      </Card>
            {/* Recent Patients */}

            <Card
        elevation={3}
        sx={{
          borderRadius: 3,
        }}
      >
        <CardContent>

          <Typography
            variant="h6"
            fontWeight="bold"
            mb={3}
          >
            Recent Patients
          </Typography>

          {/* Filters */}

          <Stack
            direction={{
              xs: "column",
              md: "row",
            }}
            spacing={2}
            mb={3}
          >

            <TextField
              label="From Date"
              type="date"
              value={fromDate}
              onChange={(e) => setFromDate(e.target.value)}
              InputLabelProps={{
                shrink: true,
              }}
            />

            <TextField
              label="To Date"
              type="date"
              value={toDate}
              onChange={(e) => setToDate(e.target.value)}
              InputLabelProps={{
                shrink: true,
              }}
            />

            <Button
              variant="contained"
              onClick={handleSearch}
            >
              Search
            </Button>

          </Stack>

          {
            loading ?

              <Box
                display="flex"
                justifyContent="center"
                py={5}
              >
                <CircularProgress />
              </Box>

              :

              <TableContainer component={Paper}>

                <Table>

                  <TableHead>

                    <TableRow>

                      <TableCell>
                        <b>ID</b>
                      </TableCell>

                      <TableCell>
                        <b>Patient Name</b>
                      </TableCell>

                      <TableCell>
                        <b>Mobile</b>
                      </TableCell>

                      <TableCell>
                        <b>Gender</b>
                      </TableCell>

                      <TableCell>
                        <b>Date Of Birth</b>
                      </TableCell>

                      <TableCell>
                        <b>Created At</b>
                      </TableCell>

                    </TableRow>

                  </TableHead>

                  <TableBody>

                    {
                      patients.length > 0 ?

                        patients.map((patient) => (

                          <TableRow
                            hover
                            key={patient.id}
                          >

                            <TableCell>
                              {patient.id}
                            </TableCell>

                            <TableCell>
                              {patient.name}
                            </TableCell>

                            <TableCell>
                              {patient.mobile}
                            </TableCell>

                            <TableCell>
                              {patient.gender}
                            </TableCell>

                            <TableCell>
                              {patient.dateOfBirth}
                            </TableCell>

                            <TableCell>
                              {
                                new Date(patient.createdAt)
                                  .toLocaleString()
                              }
                            </TableCell>

                          </TableRow>

                        ))

                        :

                        <TableRow>

                          <TableCell
                            align="center"
                            colSpan={6}
                          >

                            <Typography
                              color="text.secondary"
                              py={3}
                            >
                              No Patients Found
                            </Typography>

                          </TableCell>

                        </TableRow>

                    }

                  </TableBody>

                </Table>

              </TableContainer>

          }
                    <TablePagination
            component="div"
            count={totalRows}
            page={page}
            rowsPerPage={rowsPerPage}
            rowsPerPageOptions={[5, 10, 20, 50]}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />

        </CardContent>

      </Card>

    </Box>

  );

};

export default ReceptionDashboard;