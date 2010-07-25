

function BilliardTable()
{ 
 	var canvas;
 	var ctx;
	var center;
	var scale;
	 	
	this.draw = draw;
	this.init = init;

    var ball1 = new Ball();
    var ball2 = new Ball();
    var ball3 = new Ball();

    var cue = new Cue();
		
	function init()
	{
        canvas = document.getElementById('billiardtable');	
        ctx = canvas.getContext('2d');
        ctx.shadowOffsetX = 1;
		ctx.shadowOffsetY = 1;
		ctx.shadowBlur = 2;
		ctx.shadowColor = "black";
   		center = canvas.width/2;
    	scale = center * 0.8;
    	
		ball1.init(1,'white',190,290);
		ball2.init(2,'red',90,90);
		ball3.init(3,'yellow',150,370);

		cue.init(190,290);
    	
		draw();
		
	}

	function draw()
	{
                   
        /*table*/               
    
    	ctx.beginPath();
    	ctx.moveTo(0,0);
    	ctx.fillStyle = 'blue';
    	ctx.fillRect(0,0,canvas.width,canvas.height)
    	ctx.fill();
    	ctx.stroke();    
    	
    	ball1.draw();
    	ball2.draw();
    	ball3.draw();
    	
    	cue.draw();
	}	
}

 