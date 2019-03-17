let ctx;
let canvasWidth;
let canvasHeight;

let region;
let chosenSector = [];

const offsetPercent = 0.05;
let offset;

const canvasBackgroundColor = "rgb(243,243,243)";
const canvasBordersColor = "black";

const regionBackgroundColor = "rgb(207,226,243)";
const regionBorderColor = "black";//"rgb(165,165,165)";

const sectorBackgroundColor = "rgb(200,200,200)";
const sectorBorderColor = "black";
const sectorPointsColor = "black";

let leftPoint;
let rightPoint;
let topPoint;
let bottomPoint;

let choosingPoint = false;
let currentPoint;

function getCurrentPoint(e) {
    const rect = document.getElementById("canvas").getBoundingClientRect();
    currentPoint = {x: localXToWorld(e.clientX - rect.left), y: localYToWorld(e.clientY - rect.top)};
}

$('document').ready(function () {
    const canvas = $("canvas");
    canvas.on('mousedown', function(e) {
        const oldPoint = currentPoint;
        getCurrentPoint(e);
        if (choosingPoint) {
            currentPoint = oldPoint;
        } else if (checkPoint(region, currentPoint)) {
            choosingPoint = true;
            drawGraph();
        }
    });
    canvas.on('mousemove', function(e) {
        if (choosingPoint) {
            const oldPoint = currentPoint;
            getCurrentPoint(e);
            if (!checkPoint(region, currentPoint)) {
                currentPoint = getClosestPosition(region, currentPoint);
                console.log(currentPoint);
            }
            drawGraph();
        }
    });
    canvas.on('click', function (e) {
        if (choosingPoint) {
            chosenSector.push(currentPoint);
            if (checkNewSector(chosenSector)) {
                document.getElementById("sectorForm:x").value = currentPoint.x;
                document.getElementById("sectorForm:y").value = currentPoint.y;
                document.getElementById("sectorForm:addPoint").click();
            } else {
                chosenSector.pop();
            }
        }
        choosingPoint = false;
        drawGraph();
    })
});

function removePoint() {
    chosenSector.pop();
    choosingPoint = false;
    drawGraph();
}
function clearPoints() {
    chosenSector = [];
    choosingPoint = false;
    drawGraph();
}

function init(points) {
    let canvas = document.getElementById("canvas");
    region = points;
    getEdges(region);
    if (canvas.getContext) {
        ctx = canvas.getContext("2d");
        drawGraph();
    }
}

function getEdges(points) {
    leftPoint = rightPoint = points[0].x;
    topPoint = bottomPoint = points[0].y;
    for (let point of points) {
        leftPoint = leftPoint < point.x ? leftPoint : point.x;
        rightPoint = rightPoint > point.x ? rightPoint : point.x;
        topPoint = topPoint < point.y ? topPoint : point.y;
        bottomPoint = bottomPoint > point.y ? bottomPoint : point.y;
    }
}
function worldXToLocal(x) {
    return (x - leftPoint) / (rightPoint - leftPoint) * (canvasWidth - offset * 2) + offset;
}
function worldYToLocal(y) {
    return (y - topPoint) / (bottomPoint - topPoint) * (canvasHeight - offset * 2) + offset;
}
function localXToWorld(x) {
    return (x - offset) / (canvasWidth - offset * 2) * (rightPoint - leftPoint) + leftPoint;
}
function localYToWorld(y) {
    return (y - offset) / (canvasHeight - offset * 2) * (bottomPoint - topPoint) + topPoint;
}

function drawGraph() {
    canvasWidth = canvas.width;
    canvasHeight = canvas.height;
    offset = canvasWidth * offsetPercent;
    ctx.clearRect(0, 0, canvasWidth, canvasHeight);
    drawBackground();
    drawRegion();
    drawSector();
    if (choosingPoint) {
        drawPoint(worldXToLocal(currentPoint.x), worldYToLocal(currentPoint.y));
    }
    ctx.clearRect(0, 0, canvasWidth, canvasHeight);
    drawBackground();
    drawRegion();
    drawSector();
    if (choosingPoint) {
        drawPoint(worldXToLocal(currentPoint.x), worldYToLocal(currentPoint.y));
    }
}

function drawBackground() {
    ctx.lineWidth = 2;
    ctx.rect(0, 0, canvasWidth, canvasHeight);
    ctx.fillStyle = canvasBackgroundColor;
    ctx.fill();
    ctx.strokeStyle = canvasBordersColor;
    ctx.stroke();
}

function drawRegion() {
    ctx.lineWidth = 1;
    ctx.beginPath();
    ctx.moveTo(worldXToLocal(region[0].x), worldYToLocal(region[0].y));
    for (let point of region) {
        ctx.lineTo(worldXToLocal(point.x), worldYToLocal(point.y));
    }
    ctx.closePath();
    ctx.fillStyle = regionBackgroundColor;
    ctx.fill();
    ctx.strokeStyle = regionBorderColor;
    ctx.stroke();
}
function drawSector() {
    ctx.lineWidth = 1;
    if (chosenSector.length > 2) {
        ctx.beginPath();
        ctx.moveTo(worldXToLocal(chosenSector[0].x), worldYToLocal(chosenSector[0].y));
        for (let point of chosenSector) {
            ctx.lineTo(worldXToLocal(point.x), worldYToLocal(point.y));
        }
        ctx.closePath();
        ctx.fillStyle = sectorBackgroundColor;
        ctx.fill();
        ctx.strokeStyle = sectorBorderColor;
        ctx.stroke();
    }
    ctx.fillStyle = sectorPointsColor;
    for (let point of chosenSector) {
        drawPoint(worldXToLocal(point.x), worldYToLocal(point.y));
    }
}

function drawPoint(x, y) {
    ctx.beginPath();
    ctx.arc(x, y, 1.5, 0, Math.PI * 2);
    ctx.closePath();
    ctx.fill();
}

function drawRegionByCanvasId(canvasId, points) {
    const canvas = document.getElementById(canvasId);
    region = points;
    getEdges(region);
    if (canvas.getContext) {
        ctx = canvas.getContext("2d");
        canvasWidth = canvas.width;
        canvasHeight = canvas.height;
        offset = canvasWidth * offsetPercent;
        ctx.clearRect(0, 0, canvasWidth, canvasHeight);
        drawBackground();
        drawRegion();
    }
}
function drawSectorByCanvasId(canvasId, points) {
    const canvas = document.getElementById(canvasId);
    if (canvas.getContext) {
        ctx = canvas.getContext("2d");
        canvasWidth = canvas.width;
        canvasHeight = canvas.height;
        offset = canvasWidth * offsetPercent;
        ctx.clearRect(0, 0, canvasWidth, canvasHeight);
        drawBackground();
        drawRegion();
        chosenSector = points;
        drawSector();
    }
}