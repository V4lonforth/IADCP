function orientation(p, q, r)
{
    const val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

    if (val === 0) return 0;
    return (val > 0)? 1: 2;
}

function onSegment(p, q, r)
{
    return q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x) &&
        q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y);
}

function doIntersect(p1, q1, p2, q2)
{
    const o1 = orientation(p1, q1, p2);
    const o2 = orientation(p1, q1, q2);
    const o3 = orientation(p2, q2, p1);
    const o4 = orientation(p2, q2, q1);

    if (o1 !== o2 && o3 !== o4)
        return true;

    if (o1 === 0 && onSegment(p1, p2, q1)) return true;
    if (o2 === 0 && onSegment(p1, q2, q1)) return true;
    if (o3 === 0 && onSegment(p2, p1, q2)) return true;
    if (o4 === 0 && onSegment(p2, q1, q2)) return true;

    return false;
}
function checkNewSector(shape) {
    if (shape.length <= 3)
        return true;
    for (let i = 0; i < shape.length; i++) {
        const nextI = (i + 1) % shape.length;
        let count = 0;
        for (let j = 0; j < shape.length; j++) {
            const nextJ = (j + 1) % shape.length;
            if (doIntersect(shape[i], shape[nextI], shape[j], shape[nextJ])) {
                count++;
            }
        }
        if (count !== 3) {
            return false;
        }
    }
    return true;
}

function checkPoint(shape, point) {
    const extreme = {x: 100, y: point.y};
    let count = 0;
    for (let i = 0; i < shape.length; i++) {
        const next = (i + 1) % shape.length;
        if (doIntersect(shape[i], shape[next], point, extreme)) {
            if (orientation(shape[i], point, shape[next]) === 0) {
                return onSegment(shape[i], point, shape[next]);
            }
            count++;
        }
    }
    return count % 2 === 1;
}
function dot(p1, p2) {
    return p1.x * p2.x + p1.y * p2.y;
}
function length(p) {
    return Math.sqrt(p.x * p.x + p.y * p.y);
}
function diff(p1, p2) {
    return {x: p1.x - p2.x, y: p1.y - p2.y};
}
function sum(p1, p2) {
    return {x: p1.x + p2.x, y: p1.y + p2.y};
}
function getClosestPosition(shape, point) {
    let minDistance = 100000;
    let closestPosition;
    for (let i = 0; i < shape.length; i++) {
        const next = (i + 1) % shape.length;
        let v1 = diff(point, shape[i]);
        let v2 = diff(shape[next], shape[i]);
        if (dot(v1, v2) <= 0) {
            const distance = length(v1);
            if (distance < minDistance) {
                minDistance = distance;
                closestPosition = shape[i];
            }
        } else {
            v1 = diff(point, shape[next]);
            v2 = diff(shape[i], shape[next]);
            if (dot(v1, v2) <= 0) {
                const distance = length(v1);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPosition = shape[next];
                }
            } else {
                v2 = getNormal(shape[i], shape[next]);
                const distance = dot(v1, v2);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPosition = diff(point, {x: v2.x * distance, y: v2.y * distance});
                }
            }
        }
    }
    return closestPosition;
}

function getNormal(p1, p2) {
    const point = {x: p2.y - p1.y, y: p1.x - p2.x};
    const distance = length(point);
    return {x: -point.x / distance, y: -point.y / distance};
}