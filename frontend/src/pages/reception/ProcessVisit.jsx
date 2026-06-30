import { useState } from "react";
import { Controller, useForm } from "react-hook-form";
import { useLocation, useNavigate } from "react-router-dom";

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
  Typography,
} from "@mui/material";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import AssignmentIcon from "@mui/icons-material/Assignment";

import visitApi from "../../api/visitApi";

const ProcessVisit = () => {

  const navigate = useNavigate();

  const location = useLocation();

  const { patientId, mobile } = location.state || {};

  const [loading, setLoading] = useState(false);

  const [error, setError] = useState("");

  const [success, setSuccess] = useState("");

  const [info, setInfo] = useState("");

  const [warning, setWarning] = useState("");

  const [visit, setVisit] = useState(null);

  const {
    control,
    handleSubmit,
    getValues,
    formState: { errors },
  } = useForm({
    defaultValues: {
      patientType: "",
      paymentMode: "",
    },
  });

  const processVisit = async (formData) => {

    setLoading(true);

    setError("");
    setSuccess("");
    setInfo("");
    setWarning("");

    try {

      const response =
        await visitApi.processReceptionFlow(
          mobile,
          {
            patientId,
            patientType: formData.patientType,
            paymentMode: formData.paymentMode,
          }
        );

      setVisit(response.data);

      if (
        response.data.status === "ACTIVE"
      ) {

        setSuccess(
          "New visit created successfully"
        );

      } else {

        setInfo(
          "Active visit already exists for this patient"
        );

      }

    } catch (err) {

      const message =
        err.response?.data?.message || "";

      if (message === "VISIT_EXPIRED") {

        setWarning(
          "Previous visit has expired"
        );

      } else {

        setError(
          message ||
          "Unable to process visit."
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
          mobile,
          {
            patientId,
            patientType: values.patientType,
            paymentMode: values.paymentMode,
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

  if (!patientId || !mobile) {

    return (

      <Box sx={{ mt: 4 }}>

        <Alert severity="warning">
          No patient selected.
        </Alert>

        <Button
          variant="contained"
          sx={{ mt: 3 }}
          onClick={() =>
            navigate("/reception/register-patient")
          }
        >
          Go to Register Patient
        </Button>

      </Box>

    );

  }

  return (

    <Box>

<Stack
        direction="row"
        alignItems="center"
        spacing={1}
        sx={{
          mb: 3,
        }}
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
            Create or continue patient visit
          </Typography>

        </Box>

      </Stack>

      {/* Patient Information */}

      <Card
        elevation={2}
        sx={{
          mb: 3,
          borderRadius: 2,
        }}
      >
        <CardContent>

          <Typography
            variant="h6"
            sx={{
              mb: 2,
            }}
          >
            Processing Visit
          </Typography>

          <Grid
            container
            spacing={2}
          >

            <Grid
              item
              xs={12}
              md={6}
            >
              <Typography variant="subtitle2">
                Patient ID
              </Typography>

              <Typography>
                {patientId}
              </Typography>
            </Grid>

            <Grid
              item
              xs={12}
              md={6}
            >
              <Typography variant="subtitle2">
                Mobile Number
              </Typography>

              <Typography>
                {mobile}
              </Typography>
            </Grid>

          </Grid>

        </CardContent>
      </Card>

      {/* Visit Details */}

      <Card
        elevation={2}
        sx={{
          borderRadius: 2,
        }}
      >
        <CardContent>

          <Stack
            direction="row"
            spacing={1}
            alignItems="center"
            sx={{
              mb: 3,
            }}
          >
            <AssignmentIcon color="primary" />

            <Typography variant="h6">
              Visit Details
            </Typography>

          </Stack>

          <Grid
            container
            spacing={3}
          >

            {/* Patient Type */}

            <Grid
              item
              xs={12}
              md={6}
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
                      "Patient Type is required",
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

            {/* Payment Mode */}

            <Grid
              item
              xs={12}
              md={6}
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
                      "Payment Mode is required",
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
              item
              xs={12}
            >
              <Button
                variant="contained"
                fullWidth
                disabled={loading}
                onClick={handleSubmit(processVisit)}
                sx={{
                  py: 1.5,
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
          {success && (
            <Alert
              severity="success"
              sx={{ mt: 3 }}
            >
              {success}
            </Alert>
          )}

          {info && (
            <Alert
              severity="info"
              sx={{ mt: 3 }}
            >
              {info}
            </Alert>
          )}

          {warning && (
            <Alert
              severity="warning"
              sx={{ mt: 3 }}
            >
              {warning}
            </Alert>
          )}

          {error && (
            <Alert
              severity="error"
              sx={{ mt: 3 }}
            >
              {error}
            </Alert>
          )}

          {visit && (

            <Card
              elevation={2}
              sx={{
                mt: 4,
                borderRadius: 2,
              }}
            >
              <CardContent>

                <Typography
                  variant="h6"
                  sx={{
                    mb: 3,
                  }}
                >
                  Visit Result
                </Typography>

                <Typography
                  variant="h4"
                  color="primary"
                  sx={{
                    fontWeight: 600,
                    mb: 3,
                  }}
                >
                  {visit.opdNumber}
                </Typography>

                <Grid
                  container
                  spacing={3}
                >

                  <Grid item xs={12} md={6}>
                    <Typography variant="subtitle2">
                      Visit Date
                    </Typography>

                    <Typography>
                      {visit.visitDate}
                    </Typography>
                  </Grid>

                  <Grid item xs={12} md={6}>
                    <Typography variant="subtitle2">
                      Expiry Date
                    </Typography>

                    <Typography>
                      {visit.expiryDate}
                    </Typography>
                  </Grid>

                  <Grid item xs={12} md={6}>
                    <Typography variant="subtitle2">
                      Patient Type
                    </Typography>

                    <Typography>
                      {visit.patientType}
                    </Typography>
                  </Grid>

                  <Grid item xs={12} md={6}>
                    <Typography variant="subtitle2">
                      Payment Mode
                    </Typography>

                    <Typography>
                      {visit.paymentMode}
                    </Typography>
                  </Grid>

                  <Grid item xs={12}>
                    <Typography
                      variant="subtitle2"
                      sx={{
                        mb: 1,
                      }}
                    >
                      Status
                    </Typography>

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
                      md: "row",
                    }}
                    spacing={2}
                    sx={{
                      mt: 4,
                    }}
                  >

                    <Button
                      variant="contained"
                      color="warning"
                      disabled={loading}
                      onClick={extendVisit}
                    >
                      Extend Existing Visit
                    </Button>

                    <Button
                      variant="contained"
                      disabled={loading}
                      onClick={createNewVisit}
                    >
                      Create New Visit
                    </Button>

                  </Stack>

                )}

              </CardContent>
            </Card>

          )}

        </CardContent>
      </Card>

    </Box>

  );

};

export default ProcessVisit;