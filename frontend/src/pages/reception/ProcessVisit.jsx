import { useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import EditIcon from "@mui/icons-material/Edit";



import {
  Alert,
  Box,
  Button,
  Card,
  CardContent,
  Chip,
  CircularProgress,
  FormControl,
  FormHelperText,
  Grid,
  IconButton,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
  Typography
} from "@mui/material";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import PersonSearchIcon from "@mui/icons-material/PersonSearch";

import patientApi from "../../api/patientApi";
import visitApi from "../../api/visitApi";

const ProcessVisit = () => {

  const navigate = useNavigate();

  // Search states
  const [mobile, setMobile] = useState("");
  const [patient, setPatient] = useState(null);
  const [searching, setSearching] = useState(false);
  const [searchError, setSearchError] = useState("");

  // Visit states
  const [loading, setLoading] = useState(false);
  const [visit, setVisit] = useState(null);

  const [success, setSuccess] = useState("");
  const [info, setInfo] = useState("");
  const [warning, setWarning] = useState("");
  const [error, setError] = useState("");

  const {
    control,
    handleSubmit,
    reset,
    getValues,
    formState: { errors }
  } = useForm({
    defaultValues: {
      patientType: "",
      paymentMode: ""
    }
  });

  const searchPatient = async () => {

    if (mobile.length !== 10) {
      setSearchError("Mobile number must be exactly 10 digits.");
      return;
    }

    try {

      setSearching(true);

      setSearchError("");

      setPatient(null);

      const response =
        await patientApi.findByMobile(mobile);

      setPatient(response.data);

    } catch (err) {

      if (err.response?.status === 404) {

        setSearchError(
          "No patient found with this mobile number."
        );

      } else {

        setSearchError(
          err.response?.data?.message ||
          "Unable to search patient."
        );

      }

    } finally {

      setSearching(false);

    }

  };

  const resetScreen = () => {

    setMobile("");

    setPatient(null);

    setVisit(null);

    setSuccess("");

    setInfo("");

    setWarning("");

    setError("");

    setSearchError("");

    reset();

  };

  const processVisit = async (formData) => {

    try {

      setLoading(true);

      setSuccess("");
      setInfo("");
      setWarning("");
      setError("");

      const response =
        await visitApi.processReceptionFlow(
          patient.mobile,
          {
            patientId: patient.id,
            patientType: formData.patientType,
            paymentMode: formData.paymentMode
          }
        );

      setVisit(response.data);

      if (response.data.status === "ACTIVE") {

        setSuccess("New visit created successfully");

      } else {

        setInfo("Active visit already exists");

      }

    } catch (err) {

      const message =
        err.response?.data?.message;

      if (message === "VISIT_EXPIRED") {

        setVisit(err.response.data.data);

        setWarning("Previous visit has expired");

      } else {

        setError(
          message || "Unable to process visit."
        );

      }

    } finally {

      setLoading(false);

    }

  };

  const extendVisit = async () => {

    try {

      setLoading(true);

      const response =
        await visitApi.extendVisit(
          visit.id
        );

      setVisit(response.data);

      setWarning("");

      setSuccess(
        "Visit extended successfully"
      );

    } catch (err) {

      setError(
        err.response?.data?.message ||
        "Unable to extend visit."
      );

    } finally {

      setLoading(false);

    }

  };

  const createNewVisit = async () => {

    try {

      setLoading(true);

      const values = getValues();

      const response =
        await visitApi.createNewVisit(
          patient.mobile,
          {
            patientId: patient.id,
            patientType: values.patientType,
            paymentMode: values.paymentMode
          }
        );

      setVisit(response.data);

      setWarning("");

      setSuccess(
        "New visit created successfully"
      );

    } catch (err) {

      setError(
        err.response?.data?.message ||
        "Unable to create new visit."
      );

    } finally {

      setLoading(false);

    }

  };

  return (

    <Box>

      {/* Header */}

      <Stack
        direction="row"
        spacing={1}
        alignItems="center"
        sx={{ mb: 3 }}
      >

        <IconButton
          onClick={() => navigate("/reception")}
        >
          <ArrowBackIcon />
        </IconButton>

        <Box>

          <Typography variant="h4">

            Process Visit

          </Typography>

          <Typography color="text.secondary">

            Search patient by mobile number

          </Typography>

        </Box>

      </Stack>

      {/* Search Card */}

      <Card
        elevation={2}
        sx={{
          borderRadius: 2,
          mb: 3
        }}
      >

        <CardContent>

          <Stack
            direction="row"
            spacing={1}
            alignItems="center"
            sx={{ mb: 3 }}
          >

            <PersonSearchIcon color="primary" />

            <Typography variant="h6">

              Search Patient

            </Typography>

          </Stack>

          <Grid
            container
            spacing={2}
          >

            <Grid
              size={{
                xs: 12,
                md: 6
              }}

            >

              <TextField
                fullWidth
                label="Mobile Number"
                value={mobile}
                onChange={(e) =>
                  setMobile(e.target.value)
                }
                inputProps={{
                  maxLength: 10
                }}
                helperText="Enter patient's registered mobile number"
              />

            </Grid>

            <Grid
              size={{
                xs: 12,
                md: 6
              }}
            >

              <Button
                fullWidth
                variant="contained"
                onClick={searchPatient}
                disabled={searching}
                sx={{
                  height: "56px"
                }}
              >

                {searching ? (

                  <CircularProgress
                    size={24}
                    color="inherit"
                  />

                ) : (

                  "Search Patient"

                )}

              </Button>

            </Grid>

          </Grid>

          {searchError && (

            <Alert
              severity="error"
              sx={{ mt: 3 }}
            >

              {searchError}

              <Button
                size="small"
                sx={{ ml: 2 }}
                onClick={() =>
                  navigate("/reception/register-patient")
                }
              >

                Register New Patient

              </Button>

            </Alert>

          )}

        </CardContent>

      </Card>

      {/* Patient Information */}

      {patient && (

        <Card
          elevation={2}
          sx={{
            borderRadius: 2,
            mb: 3
          }}
        >

          <CardContent>

            <Stack
              direction="row"
              justifyContent="space-between"
              alignItems="center"
              sx={{ mb: 3 }}
            >

              <Typography variant="h6">

                Patient Information

              </Typography>

              <IconButton>

                <EditIcon />

              </IconButton>

            </Stack>

            <Grid
              container
              spacing={3}
            >

              <Grid 

              size={{
       mxs: 12,
        md: 6
    }}
    >

                <Typography
                  variant="subtitle2"
                  color="text.secondary"
                >
                  Patient Name
                </Typography>

                <Typography>

                  {patient.name}

                </Typography>

              </Grid>

              <Grid size={{
        xs: 12,
        md: 6
    }}>

                <Typography
                  variant="subtitle2"
                  color="text.secondary"
                >
                  Mobile Number
                </Typography>

                <Typography>

                  {patient.mobile}

                </Typography>

              </Grid>

              <Grid size={{
        xs: 12,
        md: 6
    }}>

                <Typography
                  variant="subtitle2"
                  color="text.secondary"
                >
                  Gender
                </Typography>

                <Typography>

                  {patient.gender}

                </Typography>

              </Grid>

              <Grid size={{
        xs: 12,
        md: 6
    }}>

                <Typography
                  variant="subtitle2"
                  color="text.secondary"
                >
                  Father / Husband Name
                </Typography>

                <Typography>

                  {patient.fatherOrHusbandName || "-"}

                </Typography>

              </Grid>

            </Grid>

          </CardContent>

        </Card>

      )}

      {/* Visit Form */}

      {patient && (

        <Card
          elevation={2}
          sx={{
            borderRadius: 2
          }}
        >

          <CardContent>

            <Typography
              variant="h6"
              sx={{
                mb: 3
              }}
            >
              Visit Details
            </Typography>

            <Grid
              container
              spacing={3}
            >

              <Grid
               size={{
                xs: 12,
                md: 6
            }}
              >

                <FormControl
                  fullWidth
                  error={!!errors.patientType}
                >

                  <InputLabel>

                    Patient Type

                  </InputLabel>

                  <Controller
                    name="patientType"
                    control={control}
                    rules={{
                      required:
                        "Patient Type is required"
                    }}
                    render={({ field }) => (

                      <Select
                        {...field}
                        label="Patient Type"
                      >

                        <MenuItem value="NEW">

                          New

                        </MenuItem>

                        <MenuItem value="OLD">

                          Old

                        </MenuItem>

                      </Select>

                    )}
                  />

                  <FormHelperText>

                    {errors.patientType?.message}

                  </FormHelperText>

                </FormControl>

              </Grid>

              <Grid
                size={{
                  xs: 12,
                  md: 6
              }}
              >

                <FormControl
                  fullWidth
                  error={!!errors.paymentMode}
                >

                  <InputLabel>

                    Payment Mode

                  </InputLabel>

                  <Controller
                    name="paymentMode"
                    control={control}
                    rules={{
                      required:
                        "Payment Mode is required"
                    }}
                    render={({ field }) => (

                      <Select
                        {...field}
                        label="Payment Mode"
                      >

                        <MenuItem value="CASH">

                          Cash

                        </MenuItem>

                        <MenuItem value="INSURANCE">

                          Insurance

                        </MenuItem>

                        <MenuItem value="FREE">

                          Free

                        </MenuItem>

                      </Select>

                    )}
                  />

                  <FormHelperText>

                    {errors.paymentMode?.message}

                  </FormHelperText>

                </FormControl>

              </Grid>

              <Grid
                size={{
                  xs: 12
                  
              }}
              >

                <Button
                  variant="contained"
                  fullWidth
                  disabled={loading}
                  onClick={handleSubmit(processVisit)}
                  sx={{
                    py: 1.5
                  }}
                >

                  {loading ? (

                    <CircularProgress
                      size={24}
                      color="inherit"
                    />

                  ) : (

                    "Process Visit"

                  )}

                </Button>

              </Grid>

            </Grid>

          </CardContent>

        </Card>

      )}
      {/* Alerts */}

      {success && (
        <Alert severity="success" sx={{ mt: 3 }}>
          {success}
        </Alert>
      )}

      {info && (
        <Alert severity="info" sx={{ mt: 3 }}>
          {info}
        </Alert>
      )}

      {warning && (
        <Alert severity="warning" sx={{ mt: 3 }}>
          {warning}
        </Alert>
      )}

      {error && (
        <Alert severity="error" sx={{ mt: 3 }}>
          {error}
        </Alert>
      )}

      {/* Visit Result */}

      {visit && (

        <Card
          elevation={2}
          sx={{
            mt: 3,
            borderRadius: 2
          }}
        >

          <CardContent>

            <Typography
              variant="h6"
              sx={{ mb: 3 }}
            >
              Visit Result
            </Typography>

            <Typography
              variant="h4"
              color="primary"
              sx={{
                fontWeight: 600,
                mb: 3
              }}
            >
              {visit.opdNumber}
            </Typography>

            <Grid
              container
              spacing={3}
            >

              <Grid size={{
        xs: 12,
        md: 6
    }}>
                <Typography variant="subtitle2">
                  Visit Date
                </Typography>

                <Typography>
                  {visit.visitDate}
                </Typography>
              </Grid>

              <Grid size={{
        xs: 12,
        md: 6
    }}>
                <Typography variant="subtitle2">
                  Expiry Date
                </Typography>

                <Typography>
                  {visit.expiryDate}
                </Typography>
              </Grid>

              <Grid size={{
        xs: 12,
        md: 6
    }}>
                <Typography variant="subtitle2">
                  Patient Type
                </Typography>

                <Typography>
                  {visit.patientType}
                </Typography>
              </Grid>

              <Grid size={{
        xs: 12,
        md: 6
    }}>
                <Typography variant="subtitle2">
                  Payment Mode
                </Typography>

                <Typography>
                  {visit.paymentMode}
                </Typography>
              </Grid>

              <Grid size={{
        xs: 12
    
    }}>
                <Chip
                  label={visit.status}
                  color="success"
                />
              </Grid>

            </Grid>

            {warning && (

              <Stack
                direction={{
                  xs: "column",
                  md: "row"
                }}
                spacing={2}
                sx={{ mt: 4 }}
              >

                <Button
                  variant="contained"
                  color="warning"
                  onClick={extendVisit}
                >
                  Extend Existing Visit
                </Button>

                <Button
                  variant="contained"
                  onClick={createNewVisit}
                >
                  Create New Visit
                </Button>

              </Stack>

            )}

            <Stack
              direction={{
                xs: "column",
                md: "row"
              }}
              spacing={2}
              sx={{ mt: 4 }}
            >

              <Button
                variant="contained"
                onClick={() =>
                  navigate("/reception")
                }
              >
                Go to Dashboard
              </Button>

              <Button
                variant="outlined"
                onClick={resetScreen}
              >
                Search Another Patient
              </Button>

            </Stack>

          </CardContent>

        </Card>

      )}
    </Box>

  );

};

export default ProcessVisit;