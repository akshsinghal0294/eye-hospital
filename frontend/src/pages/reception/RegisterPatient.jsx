import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Controller, useForm } from "react-hook-form";

import {
  Alert,
  Box,
  Button,
  Card,
  CardContent,
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
  Typography,
} from "@mui/material";

import ArrowBackIcon from "@mui/icons-material/ArrowBack";
import PersonAddIcon from "@mui/icons-material/PersonAdd";

import patientApi from "../../api/patientApi";

const RegisterPatient = () => {
  const navigate = useNavigate();

  const [loading, setLoading] = useState(false);

  const [error, setError] = useState("");

  const [success, setSuccess] = useState("");

  const [registeredPatient, setRegisteredPatient] = useState(null);

  const {
    register,
    control,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      name: "",
      mobile: "",
      dateOfBirth: "",
      gender: "",
      fatherOrHusbandName: "",
      address: "",
    },
  });

  const onSubmit = async (data) => {
    setLoading(true);
    setError("");
    setSuccess("");

    try {
      const response = await patientApi.registerPatient(data);

      setRegisteredPatient(response.data);

      setSuccess("Patient registered successfully");

      reset();

    } catch (err) {

      if (
        err.response?.data?.message
          ?.toLowerCase()
          .includes("mobile")
      ) {
        setError(
          "Patient already registered with this mobile"
        );
      } else {
        setError(
          err.response?.data?.message ||
            "Unable to register patient."
        );
      }

    } finally {
      setLoading(false);
    }
  };

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
            Register New Patient
          </Typography>

          <Typography color="text.secondary">
            Fill in patient details below
          </Typography>

        </Box>

      </Stack>

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
            <PersonAddIcon color="primary" />

            <Typography variant="h6">
              Patient Information
            </Typography>

          </Stack>
          <Grid
            container
            spacing={3}
          >

            {/* Row 1 */}

            <Grid
              item
              xs={12}
              md={6}
            >
              <TextField
                label="Patient Name"
                fullWidth
                error={!!errors.name}
                helperText={errors.name?.message}
                {...register("name", {
                  required: "Patient name is required",
                  minLength: {
                    value: 3,
                    message:
                      "Minimum 3 characters required",
                  },
                })}
              />
            </Grid>

            <Grid
              item
              xs={12}
              md={6}
            >
              <TextField
                label="Mobile Number"
                fullWidth
                error={!!errors.mobile}
                helperText={
                  errors.mobile?.message ??
                  "This will be used as unique identifier"
                }
                {...register("mobile", {
                  required: "Mobile number is required",
                  pattern: {
                    value: /^[0-9]{10}$/,
                    message:
                      "Mobile number must be exactly 10 digits",
                  },
                })}
              />
            </Grid>

            {/* Row 2 */}

            <Grid
              item
              xs={12}
              md={6}
            >
              <TextField
                label="Date of Birth"
                type="date"
                fullWidth
                InputLabelProps={{
                  shrink: true,
                }}
                {...register("dateOfBirth")}
              />
            </Grid>

            <Grid
              item
              xs={12}
              md={6}
            >
              <FormControl
                fullWidth
                error={!!errors.gender}
              >
                <InputLabel>
                  Gender
                </InputLabel>

                <Controller
                  name="gender"
                  control={control}
                  rules={{
                    required:
                      "Gender is required",
                  }}
                  render={({ field }) => (
                    <Select
                      {...field}
                      label="Gender"
                    >
                      <MenuItem value="MALE">
                        Male
                      </MenuItem>

                      <MenuItem value="FEMALE">
                        Female
                      </MenuItem>

                      <MenuItem value="OTHER">
                        Other
                      </MenuItem>

                    </Select>
                  )}
                />

                <FormHelperText>
                  {errors.gender?.message}
                </FormHelperText>

              </FormControl>
            </Grid>
                        {/* Row 3 */}

                        <Grid
              item
              xs={12}
            >
              <TextField
                label="Father / Husband Name"
                fullWidth
                {...register("fatherOrHusbandName")}
              />
            </Grid>

            {/* Row 4 */}

            <Grid
              item
              xs={12}
            >
              <TextField
                label="Address"
                fullWidth
                multiline
                rows={3}
                {...register("address")}
              />
            </Grid>

            {/* Submit Button */}

            <Grid
              item
              xs={12}
            >
              <Button
                type="submit"
                variant="contained"
                fullWidth
                disabled={loading}
                onClick={handleSubmit(onSubmit)}
                sx={{
                  mt: 1,
                  py: 1.5,
                }}
              >
                {loading ? (
                  <CircularProgress
                    size={24}
                    color="inherit"
                  />
                ) : (
                  "Register Patient"
                )}
              </Button>
            </Grid>

          </Grid>

          {error && (
            <Alert
              severity="error"
              sx={{
                mt: 3,
              }}
            >
              {error}
            </Alert>
          )}

          {success && (
            <Alert
              severity="success"
              sx={{
                mt: 3,
              }}
            >
              {success}
            </Alert>
          )}
                    {registeredPatient && (
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
                    mb: 2,
                  }}
                >
                  Registered Patient
                </Typography>

                <Grid
                  container
                  spacing={2}
                >
                  <Grid
                    item
                    xs={12}
                    md={4}
                  >
                    <Typography variant="subtitle2">
                      Name
                    </Typography>

                    <Typography>
                      {registeredPatient.name}
                    </Typography>
                  </Grid>

                  <Grid
                    item
                    xs={12}
                    md={4}
                  >
                    <Typography variant="subtitle2">
                      Mobile
                    </Typography>

                    <Typography>
                      {registeredPatient.mobile}
                    </Typography>
                  </Grid>

                  <Grid
                    item
                    xs={12}
                    md={4}
                  >
                    <Typography variant="subtitle2">
                      Gender
                    </Typography>

                    <Typography>
                      {registeredPatient.gender}
                    </Typography>
                  </Grid>
                </Grid>

                <Button
                  variant="contained"
                  color="success"
                  fullWidth
                  sx={{
                    mt: 4,
                    py: 1.5,
                  }}
                  onClick={() =>
                    navigate("/reception/process-visit", {
                      state: {
                        patientId: registeredPatient.id,
                        mobile: registeredPatient.mobile,
                      },
                    })
                  }
                >
                  Process Visit for this Patient
                </Button>

              </CardContent>
            </Card>
          )}

        </CardContent>
      </Card>

    </Box>
  );
};

export default RegisterPatient;