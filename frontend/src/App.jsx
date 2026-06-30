import { Suspense, lazy } from "react";
import {
  Box,
  Typography,
} from "@mui/material";
import {
  Navigate,
  Route,
  Routes,
} from "react-router-dom";

import PageLoader from "./components/common/PageLoader";
import PageWrapper from "./components/layout/PageWrapper";
import ProtectedRoute from "./routes/ProtectedRoute";
import RoleRoute from "./routes/RoleRoute";

const Login = lazy(() => import("./pages/auth/Login"));

const ReceptionDashboard = lazy(() =>
  import("./pages/reception/ReceptionDashboard")
);

const RegisterPatient = lazy(() =>
  import("./pages/reception/RegisterPatient")
);

const ProcessVisit = lazy(() =>
  import("./pages/reception/ProcessVisit")
);

const App = () => {
  return (
    <Suspense fallback={<PageLoader />}>
      <Routes>

        <Route
          path="/"
          element={<Navigate to="/login" replace />}
        />

        <Route
          path="/login"
          element={<Login />}
        />

        <Route element={<ProtectedRoute />}>

          <Route
            element={
              <RoleRoute
                allowedRoles={["RECEPTIONIST"]}
              />
            }
          >
            <Route
              path="/reception"
              element={
                <PageWrapper>
                  <ReceptionDashboard />
                </PageWrapper>
              }
            />

            <Route
              path="/reception/register-patient"
              element={
                <PageWrapper>
                  <RegisterPatient />
                </PageWrapper>
              }
            />

            <Route
              path="/reception/process-visit"
              element={
                <PageWrapper>
                  <ProcessVisit />
                </PageWrapper>
              }
            />
          </Route>

        </Route>

        <Route
          path="/unauthorized"
          element={
            <Box
              sx={{
                mt: 10,
                textAlign: "center",
              }}
            >
              <Typography variant="h4">
                Unauthorized
              </Typography>

              <Typography sx={{ mt: 2 }}>
                You are not allowed to access this page.
              </Typography>
            </Box>
          }
        />

      </Routes>
    </Suspense>
  );
};

export default App;