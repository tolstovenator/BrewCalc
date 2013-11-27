Type=StaticCode
Version=1.50
@EndOfDesignText@
'Code module
'Subs in this code module will be accessible from all modules.
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
End Sub

Sub New(pnlNumericUpDown As Panel, btnMinus As Button, btnPlus As Button, txtNumber As EditText, intMin As Int, intMax As Int)
 	Dim lstMinMax As List
	lstMinMax.Initialize
	lstMinMax.Add(intMin)
	lstMinMax.Add(intMax)	
	
	pnlNumericUpDown.Initialize("")

	txtNumber.Initialize("txtNumber")
	txtNumber.InputType=txtNumber.INPUT_TYPE_NUMBERS
	txtNumber.Text=0
	txtNumber.Gravity = Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL
	txtNumber.TextSize=16
	txtNumber.Tag=lstMinMax

	btnMinus.Initialize("btnUpDown")
	btnMinus.TextSize=20
	btnMinus.Text="-"
	btnMinus.Tag=txtNumber
	
	btnPlus.Initialize("btnUpDown")
	btnPlus.TextSize=20
	btnPlus.Text="+"
	btnPlus.Tag=txtNumber	
	
	pnlNumericUpDown.AddView(btnMinus,0,0,40dip,50dip)
	pnlNumericUpDown.AddView(txtNumber,36dip,0,90dip,50dip)
	pnlNumericUpDown.AddView(btnPlus,122dip,0,40dip,50dip)
End Sub
