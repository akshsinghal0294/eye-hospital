import { Box } from "@mui/material";

import Navbar from "./Navbar";

const PageWrapper = ({ children }) => {
  return (
    <Box
      sx={{
        minHeight: "100vh",
        bgcolor: "grey.100",
      }}
    >
      <Navbar />

      <Box
        component="main"
        sx={{
          mt: "64px",
          p: 3,
          minHeight: "calc(100vh - 64px)",
        }}
      >
        {children}
      </Box>
    </Box>
  );
};

export default PageWrapper;