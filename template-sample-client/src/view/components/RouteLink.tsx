/** @jsxImportSource @emotion/react */
import { css } from '@emotion/react';
import { Button, ButtonTypeMap } from '@mui/material';
import { ApplicationPath } from 'generated/routing/ApplicationPath.generated';
import { routerPathMap } from 'generated/routing/routerPathMap.generated';
import { EmotionStyles } from 'interfaces';
import { PropsWithChildren } from 'react';
import { Link, useMatch } from 'react-router-dom';
import { assertUnreachable } from 'utils';
import { getValue } from 'utils/nominal-class';
import { buildPath } from 'utils/routing-utils';

const linkDefaultElement = 'Link';

const RouteLinkBase = (
  props: PropsWithChildren<{
    path: ApplicationPath;
    doesMatch: boolean;
    variant?: ButtonTypeMap['props']['variant'];
    addCss?: EmotionStyles;
    activeCss?: EmotionStyles;
    element?: 'Link' | 'Button';
  }>
) => {
  const properties = {
    to: buildPath(props.path),
    variant: props.variant ?? 'outlined',
    css: css([
      css`
        font-weight: ${props.doesMatch ? 'bold' : 'normal'};
        text-decoration: ${props.doesMatch ? 'underline' : 'none'};

        &:hover {
          text-decoration: underline;
        }
      `,
      props.addCss,
      props.doesMatch ? props.activeCss : undefined
    ])
  };
  const element = props.element ?? linkDefaultElement;
  // FIXME pb active css passe avant css, pas bon !
  switch (element) {
    case 'Link':
      return <Link {...properties}>{props.children}</Link>;
    case 'Button':
      return (
        <Button component={Link} {...properties}>
          {props.children}
        </Button>
      );
    default:
      assertUnreachable(element);
  }
};

export const RouteLink = (
  props: PropsWithChildren<{
    path: ApplicationPath;
    // TODO only available for Button ? buttonVariant ?
    variant?: ButtonTypeMap['props']['variant'];
    addCss?: EmotionStyles;
    activeCss?: EmotionStyles;
    element?: 'Link' | 'Button';
  }>
) => (
  <RouteLinkBase
    path={props.path}
    doesMatch={false}
    variant={props.variant}
    addCss={props.addCss}
    activeCss={props.activeCss}
    element={props.element}
  >
    {props.children}
  </RouteLinkBase>
);

export const MatchRouteLink = (
  props: PropsWithChildren<{
    path: ApplicationPath;
    matchModel: 'FullMatch' | 'PartialMatch';
    variant?: ButtonTypeMap['props']['variant'];
    addCss?: EmotionStyles;
    activeCss?: EmotionStyles;
    element?: 'Link' | 'Button';
  }>
) => {
  const match = useMatch({
    path: getValue(routerPathMap, props.path.name),
    end: props.matchModel === 'FullMatch'
  });
  // FIXME check css works
  return (
    <RouteLinkBase
      path={props.path}
      doesMatch={!!match}
      variant={props.variant}
      addCss={props.addCss}
      activeCss={props.activeCss}
      element={props.element}
    >
      {props.children}
    </RouteLinkBase>
  );
};
