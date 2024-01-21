/** @jsxImportSource @emotion/react */
import { Global } from '@emotion/react';
import {
  createTheme,
  CssBaseline,
  StyledEngineProvider,
  ThemeProvider
} from '@mui/material';
import StylesProvider from '@mui/styles/StylesProvider';
import { PropsWithChildren } from 'react';
import { globalStyles } from 'style/global-styles';

const muiTheme = createTheme({
  typography: {
    // fontSize: fonts.baseSize,
    // htmlFontSize: fonts.baseSize
  }
});
export const StylesProviderContainer = (props: PropsWithChildren) => (
  <>
    <CssBaseline />
    <Global styles={[globalStyles]} />
    <StylesProvider injectFirst>
      <StyledEngineProvider injectFirst>
        <ThemeProvider theme={muiTheme}>{props.children}</ThemeProvider>
      </StyledEngineProvider>
    </StylesProvider>
  </>
);
