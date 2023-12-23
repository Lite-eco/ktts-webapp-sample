import { ApplicationPath } from 'generated/routing/ApplicationPath.generated';
import { router } from 'generated/routing/router.generated';
import { routerPathMap } from 'generated/routing/routerPathMap.generated';
import { useMatches, useParams } from 'react-router-dom';
import { getValue } from 'utils/nominal-class';

export const useTypedParams = <T extends ApplicationPath>(): Omit<T, 'name'> =>
  useParams() as Omit<T, 'name'>;

export const useTypedMatches = () => {
  const matches = useMatches();
  return matches.map(m => ({
    ...m,
    id: m.id as ApplicationPath['name']
  }));
};

// because of https://github.com/remix-run/react-router/issues/7634
// solution in comment #issuecomment-1306650156
export const navigateTo = (path: ApplicationPath, replaceState = false) =>
  router.navigate(buildPath(path), {
    replace: replaceState
  });

export const buildPath = (path: ApplicationPath) => {
  let pathName = getValue(routerPathMap, path.name);
  Object.entries(path)
    .filter(([k]) => k !== 'name')
    .forEach(([key, param]) => {
      if (pathName.indexOf(key) === -1) {
        throw Error(`Missing parameter ${key} in ${pathName}.`);
      }
      pathName = pathName.replace(':' + key, param);
    });
  return pathName;
};
